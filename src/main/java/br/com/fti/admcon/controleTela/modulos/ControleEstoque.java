package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.EstoqueHeader;
import br.com.fti.admcon.modulos.entidades.empresa.EstoqueItem;
import br.com.fti.admcon.modulos.servicos.SerEstoque;
import br.com.fti.admcon.util.ferramentas.FacesMessages;
import br.com.fti.admcon.util.ferramentas.R42Data;

/****************************************************************************
 * Classe controle para View da Tela do Estoque
 * 
 * @author: Bob-Odin - 26/02/2020
 ****************************************************************************/
@Named
@ViewScoped
public class ControleEstoque implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private List<EstoqueHeader> listaEstoqueHdr = new ArrayList<EstoqueHeader>();
	private EstoqueHeader estoqueHrdEdicao;
	private EstoqueHeader estoqueHrdSelect = new EstoqueHeader();

	private List<EstoqueItem> listaEstoqueItm;

	private boolean tbPeriodo;

	@Autowired
	FacesMessages mensagens;

	@Autowired
	SerEstoque serEstoque;

	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {

	}

	/****************************************************************************
	 * Buscar lista dos dados do estoque
	 ****************************************************************************/
	public void listarPeriodosInv() {
		estoqueHrdSelect = null;
		tbPeriodo = false;
		listaEstoqueHdr = serEstoque.listarTodos("D");
		RequestContext.getCurrentInstance().execute("PF('wgListaPInv').show();");
	}

	/****************************************************************************
	 * Novo Periodo
	 ****************************************************************************/
	public void novoPeriodo() {

		estoqueHrdEdicao = new EstoqueHeader();

		if (listaEstoqueHdr == null || listaEstoqueHdr.isEmpty()) {
			listaEstoqueHdr = new ArrayList<EstoqueHeader>();
			estoqueHrdEdicao.setAbertura(true);
		} else {
			estoqueHrdEdicao.setAbertura(false);
			int l = getUltimoPeriodo();
			Date ultData = listaEstoqueHdr.get(l).getDataFinal();
			estoqueHrdEdicao.setDataInicio(R42Data.adicionaDias(ultData, 1));
		}

	}

	/****************************************************************************
	 * Adicionar no periodo
	 ****************************************************************************/
	public void adicionaPeriodo() {
		boolean periodoValido = true;

		// Se a data inicial for mairo que a final - erro
		if (R42Data.inicialMaiorFinal(estoqueHrdEdicao.getDataInicio(), estoqueHrdEdicao.getDataFinal())) {

			periodoValido = false;
			mensagens.error("Data Final menor que Data Inicial!");

		} else {

			for (EstoqueHeader estoqueHeader : listaEstoqueHdr) {
				if (R42Data.conflitoPeriodos(estoqueHeader.getDataInicio(), estoqueHeader.getDataFinal(),
						estoqueHrdEdicao.getDataInicio(), estoqueHrdEdicao.getDataFinal())) {

					periodoValido = false;
					mensagens.error("Periodo informado está em conflito entre "
							+ R42Data.dateToString(estoqueHeader.getDataInicio()) + " e "
							+ R42Data.dateToString(estoqueHeader.getDataFinal()));

					break;
				}
			}
		}

		if (periodoValido) {
			salvarPeriodo();
			RequestContext.getCurrentInstance().execute("PF('wgPeriodoInventario').hide();");
		}

	}

	/****************************************************************************
	 * Salvar Periodo
	 ****************************************************************************/
	public void salvarPeriodo() {
		try {
			serEstoque.salvar(estoqueHrdEdicao);
			mensagens.info("Registro salvo com sucesso!");
			listarPeriodosInv();
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tabela"));

	}

	/****************************************************************************
	 * Remover Periodo
	 ****************************************************************************/
	public void removerPeriodo(EstoqueHeader estoqueHeader) {
		if (estoqueHeader.getEstoqueItem().size() == 0) {
			for (int i = 0; i < this.listaEstoqueHdr.size(); i++) {
				EstoqueHeader estoqueTmp = this.listaEstoqueHdr.get(i);

				if (estoqueTmp.getDataInicio().compareTo(estoqueHeader.getDataInicio()) == 0) {
					try {
						serEstoque.excluir(estoqueHeader);
						listarPeriodosInv();
					} catch (Exception e) {
						FacesContext.getCurrentInstance().validationFailed();
						mensagens.error("Necessário excluir os lançamentos antes!");
					}
				}
			}
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error("Necessário excluir os lançamentos antes!");
		}

	}

	/****************************************************************************
	 * Fecha Periodo
	 ****************************************************************************/
	public void fechaPeriodo(EstoqueHeader estoqueHeader) {
		try {
			estoqueHeader.setFechado(true);
			serEstoque.salvar(estoqueHeader);
			listarPeriodosInv();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}

	}

	/****************************************************************************
	 * Abre Periodo
	 ****************************************************************************/
	public void abrePeriodo(EstoqueHeader estoqueHeader) {
		try {
			estoqueHeader.setFechado(false);
			serEstoque.salvar(estoqueHeader);
			listarPeriodosInv();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Controle lista periodo inventário
	 ****************************************************************************/
	public int getUltimoPeriodo() {
		return 0;
	}

	public int getPrimeiroFechado() {

		int primeiro = listaEstoqueHdr.size();

		if (!listaEstoqueHdr.isEmpty()) {

			for (int i = 0; i < listaEstoqueHdr.size(); i++) {
				EstoqueHeader estoqueTmp = listaEstoqueHdr.get(i);
				if (estoqueTmp.isFechado()) {
					primeiro = i;
					break;
				}
			}
		}
		return primeiro;
	}

	/****************************************************************************
	 * Seleciona Periodo
	 ****************************************************************************/
	public void selecionaPeriodo(EstoqueHeader estoqueHeader) {
		estoqueHrdSelect = estoqueHeader;

		if(estoqueHrdSelect.isFechado()) {
			listaEstoqueItm = estoqueHeader.getEstoqueItem();
		} else {
			listaEstoqueItm = serEstoque.montaEstoque(estoqueHrdSelect);
		}
				
		
		tbPeriodo = true;
		RequestContext.getCurrentInstance().execute("PF('wgListaPInv').hide();");
		RequestContext.getCurrentInstance().update(Arrays.asList("frm"));
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public List<EstoqueHeader> getListaEstoqueHdr() {
		return listaEstoqueHdr;
	}

	public void setListaEstoqueHdr(List<EstoqueHeader> listaEstoqueHdr) {
		this.listaEstoqueHdr = listaEstoqueHdr;
	}

	public EstoqueHeader getEstoqueHrdEdicao() {
		return estoqueHrdEdicao;
	}

	public void setEstoqueHrdEdicao(EstoqueHeader estoqueHrdEdicao) {
		this.estoqueHrdEdicao = estoqueHrdEdicao;
	}

	public EstoqueHeader getEstoqueHrdSelect() {
		return estoqueHrdSelect;
	}

	public void setEstoqueHrdSelect(EstoqueHeader estoqueHrdSelect) {
		this.estoqueHrdSelect = estoqueHrdSelect;
	}

	public boolean isTbPeriodo() {
		return tbPeriodo;
	}

	public void setTbPeriodo(boolean tbPeriodo) {
		this.tbPeriodo = tbPeriodo;
	}

	public List<EstoqueItem> getListaEstoqueItm() {
		return listaEstoqueItm;
	}

	public void setListaEstoqueItm(List<EstoqueItem> listaEstoqueItm) {
		this.listaEstoqueItm = listaEstoqueItm;
	}

}

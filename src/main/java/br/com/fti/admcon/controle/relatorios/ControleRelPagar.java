package br.com.fti.admcon.controle.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.SortEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.entidade.empresa.Fornecedor;
import br.com.fti.admcon.entidade.empresa.Pagar;
import br.com.fti.admcon.servico.SerPagar;
import br.com.fti.admcon.util.FacesMessages;

/****************************************************************************
 * Classe controle para relatorio de receber
 * 
 * @author: Bob-Odin - 08/06/2017
 ****************************************************************************/
@Named
@ManagedBean
public class ControleRelPagar implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/

	private static final long serialVersionUID = 1L;

	@Autowired
	SerPagar serPagar;

	@Autowired
	FacesMessages mensagens;

	private Date lctoDe;
	private Date lctoAte;
	private Date vctoDe;
	private Date vctoAte;

	private BigDecimal somaValor;
	private BigDecimal somaAcrescimos;
	private BigDecimal somaDescontos;
	private BigDecimal somaPago;
	
	private String status = "T";
	
	private List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	private List<Pagar> lancamentos = new ArrayList<Pagar>();

	/* sem get e set - uso interno */
	private String sortColumnCase = "VCTO";
	
	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	public void iniciar() {
		fornecedores = new ArrayList<Fornecedor>();
		lancamentos = new ArrayList<Pagar>();
		RequestContext.getCurrentInstance().execute("PF('wgSelecao').show();");
	}
	
	/****************************************************************************
	 * Pesquisar
	 ****************************************************************************/
	public void pesquisar() {
		lancamentos = new ArrayList<Pagar>();
		List<Pagar> lctos = new ArrayList<Pagar>();

		if (lctoDe != null || lctoAte != null || vctoDe != null || vctoAte != null || !fornecedores.isEmpty()) {
			lctos = serPagar.listarTitulos(lctoDe, lctoAte, vctoDe, vctoAte, fornecedores);
		} else {
			mensagens.warning("Nenhum parâmetro informado");
		}

		for (Pagar pagar : lctos) {

			if (status.equalsIgnoreCase("A") && !pagar.isQuitado()) {
				lancamentos.add(pagar);
			} else if (status.equalsIgnoreCase("B") && pagar.isQuitado()) {
				lancamentos.add(pagar);
			} else if (status.equalsIgnoreCase("T")){
				lancamentos.add(pagar);
			}

		}

		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
		
	}

	/****************************************************************************
	 * Resgata lista de fornecedores selecionados
	 ****************************************************************************/
	public void retListaFornecedores(SelectEvent event) {

		fornecedores = new ArrayList<Fornecedor>();
		@SuppressWarnings("unchecked")
		List<Object> object = (List<Object>) event.getObject();

		for (final Object object2 : object) {
			fornecedores.add((Fornecedor) object2);
		}

	}
	
	/****************************************************************************
	 * Determinar coluna de ordenação
	 ****************************************************************************/
	public void onSort(SortEvent event) {
		sortColumnCase = event.getSortColumn().getHeaderText();
	}

	/****************************************************************************
	 * Soma os valores por colunas
	 ****************************************************************************/
	public void somarTotal(Object filter) {

		this.somaValor = new BigDecimal(0);
		this.somaAcrescimos = new BigDecimal(0);
		this.somaDescontos = new BigDecimal(0);
		this.somaPago = new BigDecimal(0);

		for (Pagar lancamento : lancamentos) {

			if(sortColumnCase.equalsIgnoreCase("ID")) {
				if (lancamento.getFornecedor().getIdFornecedor().toString().equalsIgnoreCase(filter.toString())) {
					somaValores(lancamento);
				}				
			} else if (sortColumnCase.equalsIgnoreCase("FORNECEDOR")) {
				if (lancamento.getFornecedor().getFantasia().equalsIgnoreCase(filter.toString())) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("LCTO")) {
				if (lancamento.getLancamento().equals(filter)) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("DOCUMENTO")) {
				if (lancamento.getDocumento().equalsIgnoreCase(filter.toString())) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("VCTO")) {
				if (lancamento.getVencimento().equals(filter)) {
					somaValores(lancamento);
				}
			}
		}
	}

	/****************************************************************************
	 * Soma os valores
	 ****************************************************************************/	
	private void somaValores(Pagar lancamento) {
		this.somaValor = this.somaValor.add(lancamento.getValor());
		this.somaAcrescimos = this.somaAcrescimos.add(lancamento.getAcrescimos());
		this.somaDescontos = this.somaDescontos.add(lancamento.getDescontos());
		this.somaPago = this.somaPago.add(lancamento.getPago());
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public List<Pagar> getLancamentos() {
		return lancamentos;
	}

	public Date getLctoDe() {
		return lctoDe;
	}

	public void setLctoDe(Date lctoDe) {
		this.lctoDe = lctoDe;
	}

	public Date getLctoAte() {
		return lctoAte;
	}

	public void setLctoAte(Date lctoAte) {
		this.lctoAte = lctoAte;
	}

	public Date getVctoDe() {
		return vctoDe;
	}

	public void setVctoDe(Date vctoDe) {
		this.vctoDe = vctoDe;
	}

	public Date getVctoAte() {
		return vctoAte;
	}

	public void setVctoAte(Date vctoAte) {
		this.vctoAte = vctoAte;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public int getTotalFornecedores() {
		if (fornecedores == null || fornecedores.isEmpty()) {
			return 0;
		} else {
			return fornecedores.size();
		}
	}

	public BigDecimal getSomaValor() {
		return somaValor;
	}

	public BigDecimal getSomaAcrescimos() {
		return somaAcrescimos;
	}

	public BigDecimal getSomaDescontos() {
		return somaDescontos;
	}

	public BigDecimal getSomaPago() {
		return somaPago;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

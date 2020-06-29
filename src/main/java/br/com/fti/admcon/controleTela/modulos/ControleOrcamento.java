package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Cliente;
import br.com.fti.admcon.modulos.entidades.empresa.OcmHeader;
import br.com.fti.admcon.modulos.entidades.empresa.OcmItem;
import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.servicos.SerOrcamento;
import br.com.fti.admcon.pesquisa.PesquisaProduto;
import br.com.fti.admcon.util.ferramentas.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Lançamento Bancário
 * 
 * @author: Bob-Odin - 22/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleOrcamento implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = -3447171970594502419L;

	private List<OcmHeader> lstOcmHeader = new ArrayList<OcmHeader>();

	private OcmHeader ocmHeaderEdicao;
	private List<OcmItem> ocmItensEdicao;
	private OcmItem ocmItem;

	private final long newItem = 9000000;
	private long nextItem = newItem;

	@Autowired
	SerOrcamento serOrcamento;

	@Autowired
	FacesMessages mensagens;

	@Autowired
	PesquisaProduto pesquisaProduto;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {

	}

	/****************************************************************************
	 * Listar orçamento
	 ****************************************************************************/
	public void listar() {
		lstOcmHeader = serOrcamento.listar();
	}

	/****************************************************************************
	 * Novo Orçamento
	 ****************************************************************************/
	public void novo() {
		ocmHeaderEdicao = new OcmHeader();
		ocmItensEdicao = new ArrayList<OcmItem>();
	}

	/****************************************************************************
	 * Salvar Orçamento
	 ****************************************************************************/
	public void salvar() {

	}

	/****************************************************************************
	 * Resgata o Cliente selecionado no dialogo
	 ****************************************************************************/
	public void clienteSelecionado(SelectEvent event) {
		ocmHeaderEdicao.setCliente((Cliente) event.getObject());
	}

	/****************************************************************************
	 * Add novo item de venda
	 ****************************************************************************/
	public void addItem() {
		nextItem++;
		OcmItem ocmItem = new OcmItem();
		ocmItem.setOcmHeader(ocmHeaderEdicao);
		ocmItem.setIdItem(this.nextItem);
		ocmItensEdicao.add(ocmItem);
	}

	/****************************************************************************
	 * Abre dialogo de pesquisa para componente
	 ****************************************************************************/
	public void pesquisaProduto(OcmItem item) {
		pesquisaProduto.abrirDialogo("pesquisaProduto");
		ocmItem = item;
	}

	/****************************************************************************
	 * Componente selecionado no dialogo de pesquisa
	 ****************************************************************************/
	public void produtoSelecionado(SelectEvent event) {
		Produto produto = (Produto) event.getObject();
		ocmItem.setProduto(produto);
	}

	/****************************************************************************
	 * Confirmar Item
	 ****************************************************************************/
	public void confirmarItem(OcmItem item) {

		ocmHeaderEdicao.getOcmItem().add(item);

	}

	/****************************************************************************
	 * Seta itens no header
	 ****************************************************************************/
	public void setItensHeader() {

		// faz a verificação de novos componentes
		for (OcmItem lItem : ocmItensEdicao) {
			if (lItem.getIdItem() != null && lItem.getIdItem() > newItem) {
				lItem.setIdItem(null);
			}
		}

		ocmHeaderEdicao.setOcmItem(null);
		ocmHeaderEdicao.setOcmItem(ocmItensEdicao);

	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public List<OcmHeader> getLstOcmHeader() {
		return lstOcmHeader;
	}

	public OcmHeader getOcmHeaderEdicao() {
		return ocmHeaderEdicao;
	}

	public void setOcmHeaderEdicao(OcmHeader ocmHeaderEdicao) {
		this.ocmHeaderEdicao = ocmHeaderEdicao;
	}

	public List<OcmItem> getOcmItensEdicao() {
		return ocmItensEdicao;
	}

	public void setOcmItensEdicao(List<OcmItem> ocmItensEdicao) {
		this.ocmItensEdicao = ocmItensEdicao;
	}
}

package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoUm;
import br.com.fti.admcon.modulos.servicos.SerProdutoUm;
import br.com.fti.admcon.util.ferramentas.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Produto Um
 * 
 * @author: Bob-Odin - 25/08/2019
 ****************************************************************************/
@Named
@ViewScoped
public class ControleProdutoUm implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private List<ProdutoUm> listaUm = new ArrayList<ProdutoUm>();
	private ProdutoUm umEdicao = new ProdutoUm();
	private ProdutoUm umSelect;

	@Autowired
	SerProdutoUm serProdutoUm;
	@Autowired
	FacesMessages mensagens;
	
	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {
	}	
	
	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		preparaTela();
		umSelect = null;
		listaUm = serProdutoUm.listarTodos();
	}	
	
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<ProdutoUm> getListaUm() {
		return listaUm;
	}

	public ProdutoUm getUmEdicao() {
		return umEdicao;
	}

	public void setUmEdicao(ProdutoUm umEdicao) {
		this.umEdicao = umEdicao;
	}

	public ProdutoUm getUmSelect() {
		return umSelect;
	}

	public void setUmSelect(ProdutoUm umSelect) {
		this.umSelect = umSelect;
	}

}

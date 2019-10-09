package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.global.UnidadeMedida;
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

	private List<UnidadeMedida> listaUm = new ArrayList<UnidadeMedida>();
	private UnidadeMedida umEdicao = new UnidadeMedida();
	private UnidadeMedida umSelect;

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

	public List<UnidadeMedida> getListaUm() {
		return listaUm;
	}

	public UnidadeMedida getUmEdicao() {
		return umEdicao;
	}

	public void setUmEdicao(UnidadeMedida umEdicao) {
		this.umEdicao = umEdicao;
	}

	public UnidadeMedida getUmSelect() {
		return umSelect;
	}

	public void setUmSelect(UnidadeMedida umSelect) {
		this.umSelect = umSelect;
	}

}

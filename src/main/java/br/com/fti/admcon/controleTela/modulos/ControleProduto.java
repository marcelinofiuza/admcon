package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.servicos.SerProduto;
import br.com.fti.admcon.util.ferramentas.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Produto Um
 * 
 * @author: Bob-Odin - 25/08/2019
 ****************************************************************************/
@Named
@ViewScoped
public class ControleProduto implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private List<Produto> listaProdutos = new ArrayList<Produto>();
	private Produto produtoEdicao = new Produto();
	private Produto produtoSelect;
	
	
	@Autowired
	SerProduto serProduto;
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
		produtoSelect = null;
		listaProdutos = serProduto.listarTodos();
	}

	/****************************************************************************
	 * Salvar registro
	 ****************************************************************************/	
	public void salvar() {
		
	}

	/****************************************************************************
	 * Iniciar um novo cadastro
	 ****************************************************************************/	
	public void novoCadastro() {
		
	}

	/****************************************************************************
	 * Editar Registro
	 ****************************************************************************/	
	public void editCadastro() {
		produtoEdicao = produtoSelect;
	}

	/****************************************************************************
	 * Excluir Registro
	 ****************************************************************************/	
	public void excluir() {
		
	}
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public Produto getProdutoEdicao() {
		return produtoEdicao;
	}

	public void setProdutoEdicao(Produto produtoEdicao) {
		this.produtoEdicao = produtoEdicao;
	}

	public Produto getProdutoSelect() {
		return produtoSelect;
	}

	public void setProdutoSelect(Produto produtoSelect) {
		this.produtoSelect = produtoSelect;
	}	
		
}

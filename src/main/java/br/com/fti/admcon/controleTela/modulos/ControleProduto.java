package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoGrupo;
import br.com.fti.admcon.modulos.entidades.global.Categoria;
import br.com.fti.admcon.modulos.entidades.global.UnidadeMedida;
import br.com.fti.admcon.modulos.servicos.SerProduto;
import br.com.fti.admcon.modulos.servicos.SerProdutoCategoria;
import br.com.fti.admcon.modulos.servicos.SerProdutoGrupo;
import br.com.fti.admcon.modulos.servicos.SerProdutoUm;
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

	private List<ProdutoGrupo> grupos;
	private List<Categoria> categorias;
	private List<UnidadeMedida> produtoUMs;

	@Autowired
	SerProduto serProduto;
	@Autowired
	SerProdutoGrupo serProdutoGrupo;
	@Autowired
	SerProdutoCategoria serProdutoCategoria;
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
		produtoSelect = null;
		listaProdutos = serProduto.listarTodos();
		grupos = serProdutoGrupo.listarTodos();
		categorias = serProdutoCategoria.listarTodos();
		produtoUMs = serProdutoUm.listarTodos();
	}

	/****************************************************************************
	 * Salvar registro
	 ****************************************************************************/
	public void salvar() {
		try {
			serProduto.salvar(produtoEdicao);
			listar();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
	}

	/****************************************************************************
	 * Iniciar um novo cadastro
	 ****************************************************************************/
	public void novoCadastro() {
		preparaTela();
		produtoEdicao = new Produto();
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
		try {
			serProduto.excluir(produtoSelect);
			produtoSelect = null;
			listar();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
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

	public List<ProdutoGrupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<ProdutoGrupo> grupos) {
		this.grupos = grupos;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<UnidadeMedida> getProdutoUMs() {
		return produtoUMs;
	}

	public void setProdutoUMs(List<UnidadeMedida> produtoUMs) {
		this.produtoUMs = produtoUMs;
	}

}

package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoCategoria;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoComponente;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoGrupo;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoMedida;
import br.com.fti.admcon.modulos.servicos.SerProduto;
import br.com.fti.admcon.modulos.servicos.SerProdutoCategoria;
import br.com.fti.admcon.modulos.servicos.SerProdutoGrupo;
import br.com.fti.admcon.modulos.servicos.SerProdutoMedida;
import br.com.fti.admcon.pesquisa.PesquisaProduto;
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
	private List<ProdutoCategoria> categorias;
	private List<ProdutoMedida> medidas;

	private final long newItem = 9000000;
	private long nextItem = newItem;

	private List<ProdutoComponente> componentes;
	private ProdutoComponente itemComponente;

	@Autowired
	SerProduto serProduto;
	@Autowired
	SerProdutoGrupo serProdutoGrupo;
	@Autowired
	SerProdutoCategoria serProdutoCategoria;
	@Autowired
	SerProdutoMedida serProdutoMedida;
	@Autowired
	PesquisaProduto pesquisaProduto;

	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Reseta as variaveis para inclusão ou alteração
	 ****************************************************************************/
	@PostConstruct
	public void preparaTela() {

		// Dados para montar lists
		grupos = serProdutoGrupo.listarTodos();
		categorias = serProdutoCategoria.listarTodos();
		medidas = serProdutoMedida.listarTodos();
		componentes = new ArrayList<>();

	}

	/****************************************************************************
	 * refresh panel
	 ****************************************************************************/
	public void refresh() {
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:panel-dados"));
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
		try {

			// faz a verificação de novos componentes
			for (ProdutoComponente componente : componentes) {
				if (componente.getIdComponente() != null && componente.getIdComponente() > newItem) {
					componente.setIdComponente(null);
				}
			}

			produtoEdicao.setComponentes(null);
			produtoEdicao.setComponentes(componentes);
			serProduto.salvar(produtoEdicao);

			listar();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));

		} catch (Exception e) {

			for (ProdutoComponente componente : componentes) {
				if (componente.getIdComponente() == null) {
					nextItem++;
					componente.setIdComponente(nextItem);
				}
			}

			RequestContext.getCurrentInstance().addCallbackParam("exceptionThrown", true);
			mensagens.error(e.getMessage());
		}
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
	 * Edita estoque do produto selecionado
	 ****************************************************************************/
	public void editEstoque() {

		if (produtoSelect.getCategoria().isEstoque()) {

			produtoEdicao = produtoSelect;
			RequestContext.getCurrentInstance().execute("PF('wgDadosEstoque').show();");

		} else {
			mensagens.warning(
					"Categoria do produto " + produtoSelect.getCategoria().getDescricao() + " não controla estoque");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
		}
	}
	
	/****************************************************************************
	 * Edita componente produto selecionado
	 ****************************************************************************/
	public void editComponente() {
		componentes = null;

		if (produtoSelect.getCategoria().isComponente()) {

			componentes = produtoSelect.getComponentes();
			RequestContext.getCurrentInstance().execute("PF('wgDadosCpt').show();");

		} else {
			mensagens.warning(
					"Categoria do produto " + produtoSelect.getCategoria().getDescricao() + " não permite componente");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
		}
	}

	/****************************************************************************
	 * Add Componente
	 ****************************************************************************/
	public void addComponente() {
		nextItem++;
		ProdutoComponente componente = new ProdutoComponente();
		componente.setProduto(produtoSelect);
		componente.setIdComponente(this.nextItem);
		componentes.add(componente);
	}

	/****************************************************************************
	 * Remove Componente
	 ****************************************************************************/
	public void removeComponente(ProdutoComponente componente) {
		componentes.remove(componente);
	}

	/****************************************************************************
	 * Abre dialogo de pesquisa para componente
	 ****************************************************************************/
	public void pesquisaComponente(ProdutoComponente componente) {
		pesquisaProduto.abrirDialogo("pesquisaProduto");
		itemComponente = componente;
	}

	/****************************************************************************
	 * Componente selecionado no dialogo de pesquisa
	 ****************************************************************************/
	public void componenteSelecionado(SelectEvent event) {
		Produto prdCpt = (Produto) event.getObject();
		itemComponente.setItemProduto(prdCpt);
	}

	/****************************************************************************
	 * Validar componente
	 ****************************************************************************/
	public void validarComponentes() {

		try {

			produtoSelect.setComponentes(componentes);
			produtoEdicao = serProduto.validaComponentes(produtoSelect);
			salvar();

			RequestContext.getCurrentInstance().execute("PF('wgDadosCpt').hide();");

		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}

	}

	/****************************************************************************
	 * Add Unidade de Medida
	 ****************************************************************************/
	public void addMedida() {
		medidas.add(new ProdutoMedida());
	}

	/****************************************************************************
	 * Remove Unidade de Medida
	 ****************************************************************************/
	public void removeMedida(ProdutoMedida medida) {

		if (medida.getIdMedida() != null) {

			try {
				serProdutoMedida.excluir(medida);
				medidas.remove(medida);
				mensagens.info("Unidade de Medida excluida com sucesso!");
			} catch (Exception e) {
				mensagens.error(e.getMessage());
			}

		} else {
			medidas.remove(medida);
			mensagens.info("Unidade de Medida removida com sucesso!");
		}

	}

	/****************************************************************************
	 * Salvar Unidade de Medida
	 ****************************************************************************/
	public void salvarUM() {

		try {
			medidas = serProdutoMedida.salvar(medidas);
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}

	}

	/****************************************************************************
	 * Add Categoria
	 ****************************************************************************/
	public void addCategoria() {
		categorias.add(new ProdutoCategoria());
	}

	/****************************************************************************
	 * Remove Categoria
	 ****************************************************************************/
	public void removeCategoria(ProdutoCategoria categoria) {

		if (categoria.getIdCategoria() != null) {

			try {
				serProdutoCategoria.excluir(categoria);
				categorias.remove(categoria);
				mensagens.info("Categoria excluida com sucesso!");
			} catch (Exception e) {
				mensagens.error(e.getMessage());
			}

		} else {
			categorias.remove(categoria);
			mensagens.info("Categoria removida com sucesso!");
		}
	}

	/****************************************************************************
	 * Salvar Categoria
	 ****************************************************************************/
	public void salvarCategoria() {

		try {
			categorias = serProdutoCategoria.salvar(categorias);
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}

	}

	/****************************************************************************
	 * Add Grupo
	 ****************************************************************************/
	public void addGrupo() {
		grupos.add(new ProdutoGrupo());
	}

	/****************************************************************************
	 * Remove Grupo
	 ****************************************************************************/
	public void removeGrupo(ProdutoGrupo grupo) {

		if (grupo.getIdGrupo() != null) {

			try {
				serProdutoGrupo.excluir(grupo);
				grupos.remove(grupo);
				mensagens.info("Grupo excluido com sucesso!");
			} catch (Exception e) {
				mensagens.error(e.getMessage());
			}

		} else {
			grupos.remove(grupo);
			mensagens.info("Grupo removido com sucesso!");
		}
	}

	/****************************************************************************
	 * Salvar Grupo
	 ****************************************************************************/
	public void salvarGrupo() {

		try {
			grupos = serProdutoGrupo.salvar(grupos);
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}

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

	public List<ProdutoCategoria> getCategorias() {
		return categorias;
	}

	public List<ProdutoMedida> getMedidas() {
		return medidas;
	}

	public List<ProdutoComponente> getcomponentes() {
		return componentes;
	}

}

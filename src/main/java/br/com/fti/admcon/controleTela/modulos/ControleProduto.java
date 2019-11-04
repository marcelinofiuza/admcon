package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoComponente;
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

	private final long newItem = 9000000;
	private long nextItem = newItem;
	private List<ProdutoComponente> componentes;

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

		// Dados para montar lists
		grupos = serProdutoGrupo.listarTodos();
		categorias = serProdutoCategoria.listarTodos();
		produtoUMs = serProdutoUm.listarTodos();
	}

	/****************************************************************************
	 * Salvar registro
	 ****************************************************************************/
	public void salvar() {
		try {

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
				if (componente.getIdComponente() == null ) {
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
		componentes = produtoEdicao.getComponentes();
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
	 * Add Componente
	 ****************************************************************************/
	public void addComponente() {
		nextItem++;
		ProdutoComponente componente = new ProdutoComponente();
		componente.setProduto(produtoEdicao);
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
	 * Edita Produto
	 ****************************************************************************/
	public void onCellEdit(CellEditEvent event) {

		try {

			Long oldValue = (Long) event.getOldValue();
			Long newValue = (Long) event.getNewValue();
			int row = event.getRowIndex();

			ProdutoComponente pc;

			if (newValue != null && !newValue.equals(oldValue)) {

				pc = componentes.get(row);

				Produto lProduto = serProduto.buscarPorId(newValue);

				if (lProduto != null && lProduto.getIdProduto() != null) {
					pc.setItemProduto(lProduto);
					pc.setQtdUtilizada(new BigDecimal(1));
					componentes.set(row, pc);
				}

			}

		} catch (Exception e) {

		}

	}

	/****************************************************************************
	 * Validar componente
	 ****************************************************************************/
	public void validarComponentes() {

		try {
			produtoEdicao.setComponentes(componentes);
			produtoEdicao = serProduto.validaComponentes(produtoEdicao);
			mensagens.info("Componentes validados!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:panel-dados"));

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

	public List<ProdutoComponente> getcomponentes() {
		return componentes;
	}

	public void setProdutoComponentes(List<ProdutoComponente> componentes) {
		this.componentes = componentes;
	}

}

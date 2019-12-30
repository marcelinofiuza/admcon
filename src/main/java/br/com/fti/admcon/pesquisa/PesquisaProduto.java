package br.com.fti.admcon.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.servicos.SerProduto;

/****************************************************************************
 * Classe controle para View de Pesquisa de Produtos
 * 
 * @author: Bob-Odin - 04/11/2019
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaProduto extends AbstrataPesquisa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = -7884598915914609037L;
	private List<Produto> listaProdutos = new ArrayList<Produto>();
	private String descricao = "";

	@Autowired
	SerProduto serProduto;

	/****************************************************************************
	 * Buscar lista de produtos
	 ****************************************************************************/
	public void listarProdutos() {
		listaProdutos.clear();
		listaProdutos = serProduto.listarPorDescricao(descricao);
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

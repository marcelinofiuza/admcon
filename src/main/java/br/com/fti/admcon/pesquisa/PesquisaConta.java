package br.com.fti.admcon.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.entidade.empresa.Conta;
import br.com.fti.admcon.servico.SerConta;

/****************************************************************************
 * Classe controle para View de Pesquisa de Conta Contábil
 * 
 * @author: Bob-Odin - 11/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaConta extends AbstrataPesquisa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private TreeNode treeContas;

	@Autowired
	SerConta serConta;

	private String contaReduzida = "";
	private String descricao = "";

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	@PostConstruct
	public void listar() {
		treeContas = serConta.listarTodos();
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void pesquisaConta() {
		List<Conta> listaConta;
		if (this.contaReduzida != null && !this.contaReduzida.isEmpty()) {
			listaConta = new ArrayList<>();
			Conta conta = serConta.buscarPorReduzida(this.contaReduzida);
			listaConta.add(conta);
			treeContas = serConta.transformaTree(listaConta);
		} else if(this.descricao != null && !this.descricao.isEmpty()) {
			listaConta = serConta.buscarPorDescricao(this.descricao);
			treeContas = serConta.transformaTree(listaConta);
		} else {
			treeContas = serConta.listarTodos();	
		}		
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public TreeNode getTreeContas() {
		return treeContas;
	}

	public String getContaReduzida() {
		return contaReduzida;
	}

	public void setContaReduzida(String contaReduzida) {
		this.contaReduzida = contaReduzida;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

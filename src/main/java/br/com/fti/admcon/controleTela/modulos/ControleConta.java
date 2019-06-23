package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.enums.AnaliticaSintetica;
import br.com.fti.admcon.modulos.entidades.empresa.Conta;
import br.com.fti.admcon.modulos.servicos.SerConta;
import br.com.fti.admcon.util.ferramentas.FacesMessages;
import br.com.fti.admcon.util.ferramentas.R42Util;



/****************************************************************************
 * Classe controle para View da Tela do Plano de contas
 * 
 * @author: Bob-Odin - 31/01/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleConta implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private TreeNode treeContas;
	private TreeNode contaSelect;
	private Conta contaEdicao = new Conta();

	@Autowired
	SerConta serConta;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Metodo Salvar
	 ****************************************************************************/
	public void salvar() {
		try {
			serConta.salvar(contaEdicao);
			listar();
			contaSelect = null;
			contaEdicao = new Conta();
			mensagens.info("Registro salvo com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela", "frm:toolbar"));
	}

	/****************************************************************************
	 * Metodo Excluir
	 ****************************************************************************/
	public void excluir() {
		try {
			Conta contatmp = (Conta) contaSelect.getData();
			serConta.excluir(contatmp);
			listar();
			contaSelect = null;
			contaEdicao = new Conta();
			mensagens.info("Registro excluido com sucesso!");
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela", "frm:toolbar"));
	}

	/****************************************************************************
	 * Buscar lista dos dados no banco
	 ****************************************************************************/
	public void listar() {
		
		treeContas = serConta.listarTodos();
		
		if(treeContas.getChildCount() == 0) {
			List<Conta> listaConta = R42Util.criarContaRaiz();
			for (Conta conta : listaConta) {
				try {
					serConta.salvar(conta);
					treeContas = serConta.listarTodos();
				} catch (Exception e) {
					mensagens.error(e.getMessage());
				}
			}
		}
		
	}

	/****************************************************************************
	 * Preparar objetos para novo cadastro
	 ****************************************************************************/
	public void novoCadastro() {

		Conta contatmp = (Conta) contaSelect.getData();

		if (contatmp.getTipoConta().equals(AnaliticaSintetica.ANALITICA)) {
			contatmp = (Conta) contaSelect.getData();
			contatmp = contatmp.getContaPai();
		}

		contaEdicao = new Conta();
		contaEdicao.setContaPai(contatmp);
		contaEdicao.setNatureza(contatmp.getNatureza());
		contaEdicao.setStatus(contatmp.getStatus());

	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editCadastro() {
		contaEdicao = (Conta) contaSelect.getData();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public TreeNode getTreeContas() {
		return treeContas;
	}

	public Conta getContaEdicao() {
		return contaEdicao;
	}

	public void setContaEdicao(Conta contaEdicao) {
		this.contaEdicao = contaEdicao;
	}

	public TreeNode getContaSelect() {
		return contaSelect;
	}

	public void setContaSelect(TreeNode contaSelect) {
		this.contaSelect = contaSelect;
	}

}

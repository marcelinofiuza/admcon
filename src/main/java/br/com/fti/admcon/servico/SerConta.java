package br.com.fti.admcon.servico;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.entidade.empresa.Conta;
import br.com.fti.admcon.repositorio.empresa.RepConta;

/****************************************************************************
 * Classe Serviço Regras de negócio da Conta Contábil Desenvolvido por :
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
@Service
@Transactional
public class SerConta {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepConta repConta;

	private TreeNode raiz;

	/****************************************************************************
	 * Retorna se existe alguma conta cadastrada
	 ****************************************************************************/
	public boolean existeConta() {
		if (repConta.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Validar e salvar a Conta
	 ****************************************************************************/
	public void salvar(Conta conta) throws Exception {
		try {
			// Se está inserindoo nova conta
			// adiciona a conta, como conta filho
			// processo bidirecional
			if (conta.getIdConta() == null) {
				if (conta.getContaPai() != null) {
					conta.getContaPai().getSubConta().add(conta);
					conta.setChave(conta.getContaPai().getSubConta().size());
				}
			}

			repConta.save(conta);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar e Excluir a Conta
	 ****************************************************************************/
	public void excluir(Conta conta) throws Exception {
		try {
			repConta.delete(conta);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar e Excluir todas as contas
	 ****************************************************************************/
	public void excluirTodas() throws Exception {
		try {
			repConta.deleteAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Retorna a lista em forma de Árvore das Contas
	 ****************************************************************************/
	public TreeNode listarTodos() {

		List<Conta> listaConta = repConta.buscarContaRaiz();

		if (listaConta != null) {
			// Montar TreeNode
			criarTreeNode(listaConta);
		}

		return raiz;
	}

	/****************************************************************************
	 * Retorna a lista por descrição
	 ****************************************************************************/	
	public List<Conta> listarPorReduzidaDescricao() {
		List<Conta> listaConta = repConta.findAll();
		return listaConta;
	}
	
	/****************************************************************************
	 * Transforma lista Conta para TreeNode Conta
	 ****************************************************************************/	
	public TreeNode transformaTree(final List<Conta> listaConta){				
		for (Conta conta : listaConta) {
			conta.setSubConta(null);			
		}
		criarTreeNode(listaConta);
		return raiz;
	}
	
	/****************************************************************************
	 * Transforma lista Conta para TreeNode Conta
	 ****************************************************************************/	
	public TreeNode transformaTree(final Conta conta){
		List<Conta> listaConta = new ArrayList<>();
		listaConta.add(conta);
		criarTreeNode(listaConta);
		return raiz;
	}	
		
	/****************************************************************************
	 * Retorna uma conta pela Reduzida
	 ****************************************************************************/
	public Conta buscarPorReduzida(String reduzida) {
		return repConta.findByReduzida(reduzida);
	}

	/****************************************************************************
	 * Retorna uma conta pela Descrição
	 ****************************************************************************/
	public List<Conta> buscarPorDescricao(String descricao) {
		return repConta.findByDescricaoContaining(descricao);
	}

	/****************************************************************************/
	//
	//
	// Metodos privados
	//
	//
	/****************************************************************************/

	/****************************************************************************
	 * Monta TreeNode das contas
	 ****************************************************************************/
	private void criarTreeNode(List<Conta> contaRaiz) {
		this.raiz = new DefaultTreeNode("RAIZ", null);
		adicionarNos(contaRaiz, this.raiz);
	}

	/****************************************************************************
	 * Adiciona os registros filhos no Pai
	 ****************************************************************************/
	private void adicionarNos(List<Conta> contas, TreeNode pai) {
		if(contas != null) {
			for (Conta conta : contas) {
				TreeNode no = new DefaultTreeNode(conta, pai);
				adicionarNos(conta.getSubConta(), no);
			}
		}
	}

}

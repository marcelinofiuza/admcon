package br.com.fti.admcon.pesquisalista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.fti.admcon.entidade.empresa.Cliente;
import br.com.fti.admcon.util.R42Util;

/****************************************************************************
 * Classe controle para View para selecionar lista de fornecedores
 * 
 * @author: Bob-Odin - 01/04/2018
 ****************************************************************************/
@Named
@ViewScoped
public class ListaSelecaoFornecedor extends AbstrataSelecao {

	private Cliente cliente;
	private Cliente clienteSelect;
	private List<Cliente> listaClientes = new ArrayList<Cliente>();

	/****************************************************************************
	 * Inicialização, resgata item já selecionado
	 ****************************************************************************/
	public void preparaTela() {

		Map<String, Object> objeto = new HashMap<>();
		objeto = R42Util.getAtributo("LISTA");

		@SuppressWarnings("unchecked")
		List<Object> listaInicial = (List<Object>) objeto.get("ENTIDADE");

		for (Object cliente : listaInicial) {
			listaClientes.add((Cliente) cliente);
		}

	}

	/****************************************************************************
	 * Resgata o cliente selecionada no dialogo
	 ****************************************************************************/
	public void clienteSelecionado(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	/****************************************************************************
	 * Adicona cliente na lista de cliente
	 ****************************************************************************/
	public void adicionaCliente() {
		if (cliente != null) {
			listaClientes.add(cliente);
			cliente = null;
		}
	}

	/****************************************************************************
	 * Remove o cliente seleciona da lista
	 ****************************************************************************/
	public void removeCliente() {
		if (clienteSelect != null) {
			listaClientes.remove(clienteSelect);
			clienteSelect = null;
		}
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Cliente getClienteSelect() {
		return clienteSelect;
	}

	public void setClienteSelect(Cliente clienteSelect) {
		this.clienteSelect = clienteSelect;
	}

	public boolean isListaVazia() {
		if (listaClientes == null || listaClientes.isEmpty()) {
			return true;
		}
		return false;
	}

}

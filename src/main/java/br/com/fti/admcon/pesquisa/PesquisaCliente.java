package br.com.fti.admcon.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.entidade.empresa.Cliente;
import br.com.fti.admcon.servico.SerCliente;

/****************************************************************************
 * Classe controle para View de Pesquisa de Clientes
 * 
 * @author: Bob-Odin - 04/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaCliente extends AbstrataPesquisa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Cliente> listaClientes = new ArrayList<Cliente>();
	private String razaoSocial = "";
	private String unidade = "";

	@Autowired
	SerCliente serCliente;

	/****************************************************************************
	 * Buscar lista de clientes
	 ****************************************************************************/
	public void listarClientes() {
		listaClientes.clear();
		listaClientes = serCliente.listarPorRazaoSocialOuUnidade(razaoSocial, unidade);
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

}

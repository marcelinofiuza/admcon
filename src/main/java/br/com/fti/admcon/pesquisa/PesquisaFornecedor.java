package br.com.fti.admcon.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Fornecedor;
import br.com.fti.admcon.modulos.servicos.SerFornecedor;

/****************************************************************************
 * Classe controle para View de Pesquisa de Fornecedor
 * 
 * @author: Thayro Rodrigues - 24/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaFornecedor extends AbstrataPesquisa implements Serializable {
	
	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 2868923029135914382L;	
	private List<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();
	
	private String razaoSocial = "";
	private String fantasia = "";
	
	@Autowired
	SerFornecedor serFornecedor;
	
	/****************************************************************************
	 * Buscar lista de fornecedor
	 ****************************************************************************/
	public void listarFornecedor() {
		listaFornecedores.clear();
		listaFornecedores = serFornecedor.listarPorRazaoSocialOuFantasia(razaoSocial, fantasia);
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	
	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public SerFornecedor getSerFornecedor() {
		return serFornecedor;
	}

	public void setSerFornecedor(SerFornecedor serFornecedor) {
		this.serFornecedor = serFornecedor;
	}
	
	
	

}

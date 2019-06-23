package br.com.fti.admcon.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.global.Empresa;
import br.com.fti.admcon.modulos.servicos.SerEmpresa;

/****************************************************************************
 * Classe controle para View de Pesquisa de Empresas
 * 
 * @author: Bob-Odin - 03/02/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaEmpresa extends AbstrataPesquisa implements Serializable {
	
	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	private List<Empresa> listaEmpresas;
	private String razaoSocial = "";
	private String fantasia = "";

	@Autowired
	SerEmpresa serEmpresa;

	/****************************************************************************
	 * Buscar lista de empresas
	 ****************************************************************************/
	public void listarEmpresas() {
		listaEmpresas = serEmpresa.listarPorRazaoSocialOuFantasia(razaoSocial, fantasia);
	}
		
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
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

}

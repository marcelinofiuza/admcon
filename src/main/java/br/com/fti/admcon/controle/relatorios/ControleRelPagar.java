package br.com.fti.admcon.controle.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.entidade.empresa.Fornecedor;
import br.com.fti.admcon.entidade.empresa.Pagar;
import br.com.fti.admcon.servico.SerPagar;
import br.com.fti.admcon.util.FacesMessages;

/****************************************************************************
 * Classe controle para relatorio de receber
 * 
 * @author: Bob-Odin - 08/06/2017
 ****************************************************************************/
@Named
@ManagedBean
public class ControleRelPagar implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/

	private static final long serialVersionUID = 1L;

	@Autowired
	SerPagar serPagar;

	@Autowired
	FacesMessages mensagens;

	private Date lctoDe;
	private Date lctoAte;
	private Date vctoDe;
	private Date vctoAte;

	private List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	private List<Pagar> lancamentos = new ArrayList<Pagar>();

	/****************************************************************************
	 * Pesquisar
	 ****************************************************************************/
	public void pesquisar() {
		serPagar.listarTitulos(lctoDe, lctoAte, vctoDe, vctoAte, fornecedores);
	}

	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public Date getLctoDe() {
		return lctoDe;
	}

	public void setLctoDe(Date lctoDe) {
		this.lctoDe = lctoDe;
	}

	public Date getLctoAte() {
		return lctoAte;
	}

	public void setLctoAte(Date lctoAte) {
		this.lctoAte = lctoAte;
	}

	public Date getVctoDe() {
		return vctoDe;
	}

	public void setVctoDe(Date vctoDe) {
		this.vctoDe = vctoDe;
	}

	public Date getVctoAte() {
		return vctoAte;
	}

	public void setVctoAte(Date vctoAte) {
		this.vctoAte = vctoAte;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Pagar> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Pagar> lancamentos) {
		this.lancamentos = lancamentos;
	}

}

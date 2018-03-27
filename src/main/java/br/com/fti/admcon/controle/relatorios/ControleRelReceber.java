package br.com.fti.admcon.controle.relatorios;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.entidade.empresa.Receber;
import br.com.fti.admcon.servico.SerReceber;

/****************************************************************************
 * Classe controle para relatorio de receber
 * 
 * @author: Bob-Odin - 08/06/2017
 ****************************************************************************/
@Named
@ManagedBean
public class ControleRelReceber implements Serializable {
	
	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	
	private static final long serialVersionUID = 1L;

	@Autowired
	SerReceber serReceber;
	
	private List<Receber> lancamentos;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	public void iniciar() {
		pesquisar();
	}
	

	/****************************************************************************
	 * Pesquisar
	 ****************************************************************************/		
	public void pesquisar() {
		lancamentos = serReceber.buscarPorLancamentoEVencimento(null, null, null, null);		
	}


	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/	
	public List<Receber> getLancamentos() {
		return lancamentos;
	}

	
}

package br.com.fti.admcon.pesquisa;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Banco;
import br.com.fti.admcon.modulos.servicos.SerBanco;


/****************************************************************************
 * Classe controle para View de Pesquisa de Bancos
 * 
 * @author: Bob-Odin - 22/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class PesquisaBanco extends AbstrataPesquisa implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 30232441813740834L;
	private List<Banco> listaBancos;

	@Autowired
	SerBanco serBanco;

	/****************************************************************************
	 * Buscar lista de empresas
	 ****************************************************************************/
	@PostConstruct
	public void listarBanco() {
		listaBancos = serBanco.listarTodos();
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Banco> getListaBancos() {
		return listaBancos;
	}

}

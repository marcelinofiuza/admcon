package br.com.fti.admcon.pesquisalista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.fti.admcon.util.R42Util;

/****************************************************************************
 * Classe controle para View para selecionar lista de clientes
 * 
 * @author: Bob-Odin - 27/03/2018
 ****************************************************************************/
@Named
@ViewScoped
public class ListaSelecaoCliente extends AbstrataSelecao {

	String atributo = "LISTASELECAOCLIENTE";
	
	/****************************************************************************
	 * Inicialização, resgata item já selecionado
	 ****************************************************************************/
	@Override
	public void preparaTela() {
		
		Map<String, Object> objeto = new HashMap<>();
		objeto = R42Util.getAtributo(atributo);

		@SuppressWarnings("unchecked")
		List<Object> listaInicial = (List<Object>) objeto.get("ENTIDADE");

		for (Object entidade : listaInicial) {
			listaEntidades.add(entidade);
		}

	}	


}

package br.com.fti.admcon.pesquisalista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.context.RequestContext;

import br.com.fti.admcon.util.R42Util;

/****************************************************************************
 * Classe Abstrata para gerar lista de seleção
 * 
 * @author: Bob-Odin - 28/03/2018
 ****************************************************************************/
public abstract class AbstrataSelecao {

	/****************************************************************************
	 * Retorna o registro selecionado na lista
	 ****************************************************************************/
	public void confirmar(List<Object> entidade) {
		RequestContext.getCurrentInstance().closeDialog(entidade);
	}

	/****************************************************************************
	 * Abre o xhtml em forma de dialogo
	 ****************************************************************************/
	public void abrirDialogo(final String paginaPesquisa, final List<Object> entidade) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentWidth", "800");
		opcoes.put("contentHeight", "450");
		opcoes.put("width", "800");
		opcoes.put("height", "450");

		Map<String, Object> objeto = new HashMap<>();
		objeto.put("ENTIDADE", entidade);
		R42Util.setAtributo("LISTA", objeto);
		
		RequestContext.getCurrentInstance().openDialog("/resources/listaselecao/" + paginaPesquisa, opcoes, null);
		
	}

}

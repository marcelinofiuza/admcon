package br.com.fti.admcon.pesquisalista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.fti.admcon.util.R42Util;

/****************************************************************************
 * Classe Abstrata para gerar lista de seleção
 * 
 * @author: Bob-Odin - 28/03/2018
 ****************************************************************************/
public abstract class AbstrataSelecao {
	
	protected Object entidade;
	protected Object entidadeSelecionada;
	protected List<Object> listaEntidades = new ArrayList<Object>();
	
	
	public abstract void preparaTela();
	
	/****************************************************************************
	 * Retorna o registro selecionado na lista
	 ****************************************************************************/
	public void confirmar(final List<Object> entidade) {
		RequestContext.getCurrentInstance().closeDialog(entidade);
	}

	/****************************************************************************
	 * Abre o xhtml em forma de dialogo
	 ****************************************************************************/
	public void abrirDialogo(final String paginaPesquisa, final List<Object> entidade) {

		String nomePesquisa = paginaPesquisa.toUpperCase(); 
		
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentWidth", "800");
		opcoes.put("contentHeight", "450");
		opcoes.put("width", "800");
		opcoes.put("height", "450");

		Map<String, Object> objeto = new HashMap<>();
		objeto.put("ENTIDADE", entidade);
		R42Util.setAtributo(nomePesquisa, objeto);
		
		RequestContext.getCurrentInstance().openDialog("/resources/listaselecao/" + paginaPesquisa, opcoes, null);
		
	}

	/****************************************************************************
	 * Resgata oselecionada no dialogo
	 ****************************************************************************/
	public void objetoSelecionado(SelectEvent event) {
		entidade = event.getObject();
	}

	/****************************************************************************
	 * Adicona cliente na lista de cliente
	 ****************************************************************************/
	public void adicionar() {
		if (entidade != null) {
			listaEntidades.add(entidade);
			entidade = null;
		}
	}

	/****************************************************************************
	 * Remove o seleciona da lista
	 ****************************************************************************/
	public void remover() {
		if (entidadeSelecionada != null) {
			listaEntidades.remove(entidadeSelecionada);
			entidadeSelecionada = null;
		}
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/	
	public Object getEntidade() {
		return entidade;
	}

	public void setEntidade(Object entidade) {
		this.entidade = entidade;
	}

	public Object getEntidadeSelecionada() {
		return entidadeSelecionada;
	}

	public void setEntidadeSelecionada(Object entidadeSelecionada) {
		this.entidadeSelecionada = entidadeSelecionada;
	}

	public List<Object> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<Object> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}
		
}

package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoGrupo;

/****************************************************************************
 * Classe de conversão da lista da grupo de produtos
 * 
 * @author: Bob-Odin - 09/10/2019
 ****************************************************************************/
@FacesConverter(value = "converterGrupo")
public class ConverterGrupo implements Converter {

	ProdutoGrupo pg;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		pg = (ProdutoGrupo) value;
		if (pg != null && pg.getIdGrupo() != null) {
			component.getAttributes().put(Long.toString(pg.getIdGrupo()), pg);
			return Long.toString(pg.getIdGrupo());
		}
		return null;
	}

	
}

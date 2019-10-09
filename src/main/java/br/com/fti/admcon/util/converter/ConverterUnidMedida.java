package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.modulos.entidades.global.UnidadeMedida;

/****************************************************************************
 * Classe de conversão da lista da categoria de produtos
 * 
 * @author: Bob-Odin - 02/10/2019
 ****************************************************************************/
@FacesConverter(value = "converterUnidMedida")
public class ConverterUnidMedida implements Converter {

	UnidadeMedida um;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		um = (UnidadeMedida) value;
		if (um != null && um.getIdUm() != null) {
			component.getAttributes().put(Long.toString(um.getIdUm()), um);
			return Long.toString(um.getIdUm());
		}
		return null;
	}

}

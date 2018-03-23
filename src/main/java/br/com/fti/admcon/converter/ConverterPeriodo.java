package br.com.fti.admcon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.entidade.empresa.BancoPeriodo;

/****************************************************************************
 * Classe de conversão do list Periodo
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@FacesConverter(value = "converterPeriodo")
public class ConverterPeriodo implements Converter {

	BancoPeriodo bp;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		bp = (BancoPeriodo) value;
		if (bp != null && bp.getIdPeriodo() != null) {
			component.getAttributes().put(Long.toString(bp.getIdPeriodo()), bp);
			return Long.toString(bp.getIdPeriodo());
		}
		return null;
	}

}

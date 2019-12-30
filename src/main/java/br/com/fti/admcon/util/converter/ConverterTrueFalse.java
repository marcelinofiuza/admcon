package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.enums.TrueFalse;


/****************************************************************************
 * Classe de conversão True / False
 * 
 * @author: Bob-Odin - 27/12/2019
 ****************************************************************************/
@FacesConverter(value = "converterTrueFalse")
public class ConverterTrueFalse implements Converter {

	private TrueFalse tf;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		tf = (TrueFalse) value;
		if (tf != null && tf.getTF().toString() != null) {
			component.getAttributes().put(tf.getTF().toString(), tf);
			return tf.getTF().toString();
		}
		return null;
	}
	
}

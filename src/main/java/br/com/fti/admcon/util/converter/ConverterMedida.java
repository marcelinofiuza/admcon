package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoMedida;

/****************************************************************************
 * Classe de conversão do list Medida
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
@FacesConverter(value = "converterMedida")
public class ConverterMedida implements Converter {

	private ProdutoMedida medida;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		medida = (ProdutoMedida) value;
		if (medida != null && medida.getIdMedida() != null) {
			component.getAttributes().put(Long.toString(medida.getIdMedida()), medida);
			return Long.toString(medida.getIdMedida());
		}
		return null;
	}

}

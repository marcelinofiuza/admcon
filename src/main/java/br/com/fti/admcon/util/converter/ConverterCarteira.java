package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.modulos.entidades.empresa.BancoCarteira;

/****************************************************************************
 * Classe de conversão do list Carteira
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
@FacesConverter(value = "converterCarteira")
public class ConverterCarteira implements Converter  {

	private BancoCarteira bancoCarteira;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		bancoCarteira = (BancoCarteira) value;
		if (bancoCarteira != null && bancoCarteira.getIdCarteira() != null) {
			component.getAttributes().put(Long.toString(bancoCarteira.getIdCarteira()), bancoCarteira);
			return Long.toString(bancoCarteira.getIdCarteira());
		}
		return null;
	}
	
}

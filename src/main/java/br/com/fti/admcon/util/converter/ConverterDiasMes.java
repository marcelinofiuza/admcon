package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.enums.DiasMes;

/****************************************************************************
 * Classe de conversão do enum DiasMes
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@FacesConverter(value = "converterDiasMes")
public class ConverterDiasMes implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		DiasMes diasMes = DiasMes.valueOf(value);
		return diasMes;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			DiasMes diasMes = (DiasMes) value;
			return diasMes.toString();
		}
		return new String(); 
	}
	
}

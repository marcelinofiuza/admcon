package br.com.fti.admcon.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoCategoria;

/****************************************************************************
 * Classe de conversão da lista da categoria de produtos
 * 
 * @author: Bob-Odin - 02/10/2019
 ****************************************************************************/
@FacesConverter(value = "converterCategoria")
public class ConverterCategoria implements Converter {

	private ProdutoCategoria pc;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
			return component.getAttributes().get(value);

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		pc = (ProdutoCategoria) value;
		if (pc != null && pc.getIdCategoria() != null) {
			component.getAttributes().put(Long.toString(pc.getIdCategoria()), pc);
			return Long.toString(pc.getIdCategoria());
		}
		return null;
	}

}

package br.com.fti.admcon.cnabRemessa;

import br.com.fti.admcon.enums.Febraban;
import br.com.fti.admcon.enums.LayoutCnab;

/****************************************************************************
 * Classe para definir layout Cnab de Remessa a ser utilizado
 * 
 * @author: Bob-Odin - 17/04/2017
 ****************************************************************************/
public class SelecionarCnabRemessa {

	public static CnabRemessa getLayout(Febraban febraban, LayoutCnab layoutCnab){		
		CnabRemessa cnab = null;
		
		if(febraban == Febraban.F_237 && layoutCnab == LayoutCnab.L400){
			return new CnabRemessa237400();
		}
		
		return cnab;
	}
	
}

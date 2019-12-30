package br.com.fti.admcon.enums;

/****************************************************************************
 * Enum True ou False
 * 
 * @author Bob-Odin - 27/12/2019
 ****************************************************************************/
public enum TrueFalse {

	TRUE("Sim", true), 
	FALSE("Não", false);

	private String descricao;
	private boolean tf;
	
	
	TrueFalse(String descricao, boolean tf) {
		this.descricao = descricao;
		this.tf = tf;
	}

	public String getDescricao() {
		return this.descricao;
	}
	
	public Boolean getTF() {
		return this.tf;
	}
	
}

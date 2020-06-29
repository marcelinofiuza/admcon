package br.com.fti.admcon.enums;

/****************************************************************************
 * Enum StatusVenda para situação da venda (Orcamento/Pedido)
 * 
 * @author Bob-Odin - 15/03/2017
 ****************************************************************************/
public enum StatusVenda {

	
	A("Aberto"), 
	C("Cancelado"), 
	F("Fechado"),
	P("Pago"),
	E("Entregue");
	

	private String descricao;

	private StatusVenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}	
	
}

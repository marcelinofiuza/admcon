package br.com.fti.admcon.enums;

/****************************************************************************
 * Enum Origem Estoque
 * 
 * @author Bob-Odin - 28/03/2017
 ****************************************************************************/
public enum OrigemEstoque {

	MANUAL("Lcto Manual"), 
	COMPRA("NF Entrada"), 
	VENDAS("NF Saida");

	private String descricao;

	OrigemEstoque(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}

package br.com.fti.admcon.enums;

/****************************************************************************
 * Enum Sentido Estoque
 * 
 * @author Bob-Odin - 17/07/2019
 ****************************************************************************/
public enum SentidoEstoque {

	E("Entrada", 1.0),
	S("Saída", -1.0);

	private String descricao;
	private Double multiplicador;

	SentidoEstoque(String descricao, Double multiplicador) {
		this.descricao = descricao;
		this.multiplicador = multiplicador;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Double getMultiplicador() {
		return this.multiplicador;
	}

}

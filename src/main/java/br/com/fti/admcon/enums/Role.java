package br.com.fti.admcon.enums;

/****************************************************************************
 * Enum Natureza de Roles de Perfil
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
public enum Role {
	
	ADMIN("Administrador"),
	
	BANCO("Cadastro Banco"),
	CLIENTE("Cadastro Cliente"),
	FORNECEDOR("Cadastro Fornecedor"),
	CONTA("Cadastro Conta Contábil"),
	PRODUTO("Cadastro Produto"),
	
	CTAS_RECEBER("Contas Receber"),
	GERAR_COBRANCA("Gerar Cobrança"),
	EMISSAO_BOLETO("Emissão Boleto"),
	RETORNO_BOLETO("Retorno Boleto"),
	REL_RECEBER("Relatório Receber"),
	CTAS_PAGAR("Contas Pagar"),
	REL_PAGAR("Relatório Pagar"),
	LANCAMENTO("Lançamentos Bancário"),
	EXTRATO("Extrato Bancário"),
	
	ESTOQUE("Estoque"),
	
	EMPRESA("Cadastro Empresa"),
	USUARIO("Cadastro Usuario"),
	MIGRACAO("Migração Dados");
	
	
	private String descricao;

	private Role(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}	
}

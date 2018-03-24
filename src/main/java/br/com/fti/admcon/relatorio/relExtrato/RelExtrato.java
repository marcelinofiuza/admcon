package br.com.fti.admcon.relatorio.relExtrato;

import java.util.List;

import br.com.fti.admcon.entidade.empresa.Lancamento;
import br.com.fti.admcon.relatorio.Relatorio;
import br.com.fti.admcon.util.R42Util;

/****************************************************************************
 * Relatórido de extrato bancário
 * 
 * @author: Bob-Odin - 13/06/2017
 ****************************************************************************/
public class RelExtrato {

	public Relatorio relatorio;

	/****************************************************************************
	 * Construtor
	 ****************************************************************************/
	public RelExtrato() {
		relatorio = new Relatorio();
	}

	/****************************************************************************
	 * Seta lista de lancamentos a ser impresso
	 ****************************************************************************/
	public void setLista(List<Lancamento> lancamentos) {
		relatorio.setListaDados(lancamentos);
	}

	/****************************************************************************
	 * Seta Parametros no relatório
	 ****************************************************************************/
	public void setParametros(String parametro, Object valor) {
		relatorio.setParametro(parametro, valor);
	}

	/****************************************************************************
	 * Gera relatório de clientes
	 ****************************************************************************/
	public void gerar() throws Exception {
		relatorio.setArquivoJrxml("Extrato");
		relatorio.setParametro("empresa", R42Util.resgataEmpresa().getFantasia());
		relatorio.geraRelatorio();
	}
}

package br.com.fti.admcon.servico;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.entidade.empresa.Lancamento;
import br.com.fti.admcon.enums.OrigemLcto;
import br.com.fti.admcon.repositorio.empresa.RepLancamento;
import br.com.fti.admcon.util.R42Data;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos Bancarios:
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
@Transactional
public class SerLancamento {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepLancamento repLancamento;

	/****************************************************************************
	 * Retorna se existe algum lançamento já efetuado
	 ****************************************************************************/
	public boolean existeLcto() {
		if (repLancamento.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Lancamento Lancamento) throws Exception {
		try {
			validarSalvar(Lancamento);
			repLancamento.save(Lancamento);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir lançamento
	 ****************************************************************************/
	public void excluir(Lancamento Lancamento) throws Exception {
		try {
			validarExcluir(Lancamento);
			repLancamento.delete(Lancamento.getIdLcto());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Verifica os dados antes de salvar
	 ****************************************************************************/
	public void validarSalvar(Lancamento Lancamento) throws Exception {
		// Verifica se informou a conta contábil
		if (Lancamento.getConta().getIdConta() == null) {
			throw new Exception("Conta é obrigatório!");
		}

		if (!R42Data.dentroPeriodo(Lancamento.getDataLcto(), Lancamento.getBancoPeriodo())) {
			throw new Exception("Data fora do periodo de lançamento!");
		}

		if (Lancamento.getBancoPeriodo().isFechado()) {
			throw new Exception("Periodo de lançamento do banco " + Lancamento.getBancoPeriodo().getBanco().getIdBanco()
					+ " fechado!");
		}

		// verifica se é transferencia
		if (Lancamento.getOrigemLcto() == OrigemLcto.TRF) {			
			if (Lancamento.getTransferencia() != null) {				
				if(Lancamento.getBancoPeriodo().getBanco().getIdBanco() == Lancamento.getTransferencia().getBancoPeriodo().getBanco().getIdBanco()){
					throw new Exception("Banco destino igual banco origem");
				}				
				validarSalvar(Lancamento.getTransferencia());
			}
		}
	}

	/****************************************************************************
	 * Verifica os dados antes de excluir
	 ****************************************************************************/
	public void validarExcluir(Lancamento Lancamento) throws Exception {

		if (!R42Data.dentroPeriodo(Lancamento.getDataLcto(), Lancamento.getBancoPeriodo())) {
			throw new Exception("Data fora do periodo de lançamento!");
		}

		if (Lancamento.getBancoPeriodo().isFechado()) {
			throw new Exception("Periodo de lançamento do banco " + Lancamento.getBancoPeriodo().getBanco().getIdBanco()
					+ " fechado!");
		}

		// verifica se é transferencia
		if (Lancamento.getOrigemLcto() == OrigemLcto.TRF) {
			if (Lancamento.getTransferencia() != null) {
				validarSalvar(Lancamento.getTransferencia());
			} else {
				throw new Exception("Transferência deverá ser excluida no banco de origem!");
			}
		}
	}
}

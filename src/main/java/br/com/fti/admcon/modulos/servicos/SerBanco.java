package br.com.fti.admcon.modulos.servicos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.enums.DebitoCredito;
import br.com.fti.admcon.modulos.entidades.empresa.Banco;
import br.com.fti.admcon.modulos.entidades.empresa.BancoPeriodo;
import br.com.fti.admcon.modulos.entidades.empresa.Lancamento;
import br.com.fti.admcon.modulos.repositorio.empresa.RepBanco;
import br.com.fti.admcon.util.ferramentas.R42Data;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
@Transactional
public class SerBanco {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepBanco repBanco;

	/****************************************************************************
	 * Retorna se existe algum banco cadastro
	 ****************************************************************************/
	public boolean existeBanco() {
		if (repBanco.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Banco banco) throws Exception {
		try {
			validarSalvar(banco);
			repBanco.save(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Banco banco) throws Exception {
		try {
			validarExclusao(banco);
			repBanco.delete(banco);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para buscar o banco pelo id
	 ****************************************************************************/
	public Banco buscarPorId(Long id) {
		return repBanco.findOne(id);
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Banco> listarTodos() {
		return repBanco.findAll();
	}

	/****************************************************************************
	 * Metodo para montar lançamentos do banco para periodo especificado
	 ****************************************************************************/
	public List<Lancamento> lancamentos(Banco banco, Date inicio, Date fim) {

		List<Lancamento> lancamentos = new ArrayList<>();

		// Monta saldo dos lançamentos
		montaSaldo(banco, inicio, fim);

		// Seleciona o periodo da data inicial
		BancoPeriodo periodoInicial = selecionaPeriodo(banco, inicio);
		// Seleciona o periodo da data final
		BancoPeriodo periodoFinal = selecionaPeriodo(banco, fim);

		for (BancoPeriodo bancoPeriodo : banco.getPeriodos()) {

			// se não está no periodo inicial, vai para o proximo
			if (bancoPeriodo.getDataInicio().before(periodoInicial.getDataInicio())) {
				continue;
			}

			// se não está no periodo final, para o processo
			if (bancoPeriodo.getDataFinal().after(periodoFinal.getDataFinal())) {
				break;
			}

			for (Lancamento lancamento : bancoPeriodo.getLancamentos()) {

				// se a data está fora do periodo solicidado, proximo
				if (lancamento.getDataLcto().before(inicio)) {
					continue;
				}

				// se a data está fora do perido solicitado, finaliza
				if (lancamento.getDataLcto().after(fim)) {
					break;
				}

				// Adiciona lancamento
				lancamentos.add(lancamento);
			}
		}

		// Monta saldo inicial
		Lancamento lancamento = new Lancamento();
		BigDecimal saldoAnterior = new BigDecimal(0);
		
		try {
			saldoAnterior = lancamentos.get(0).getSaldo().subtract(lancamentos.get(0).getValorLctoConvertido());
		} catch (Exception e) {

		}
		
		lancamento.setHistorico("SALDO ANTERIOR");
		lancamento.setValorLcto(new BigDecimal(0));
		lancamento.setSaldo(saldoAnterior);

		if (saldoAnterior.signum() == -1) {
			lancamento.setTipoLcto(DebitoCredito.DEBITO);
		} else {
			lancamento.setTipoLcto(DebitoCredito.CREDITO);
		}

		lancamentos.add(0, lancamento);

		return lancamentos;

	}

	/****************************************************************************
	 * Metodo para montar os saldos dos lançamentos
	 ****************************************************************************/
	public void montaSaldo(Banco banco, Date inicio, Date fim) {

		Date dataDe = R42Data.inicioMes(inicio);
		//Date dataAte = R42Data.fimMes(fim);

		BigDecimal saldoInicial = new BigDecimal(0);

		for (BancoPeriodo periodo : banco.getPeriodos()) {

			// se é abertura, existe saldo inicial
			if (periodo.isAbertura()) {
				saldoInicial = periodo.getSaldoInicial();
			}

			// se o periodo é antes do solicitado e está fechado, não processa
			if (periodo.getDataInicio().compareTo(dataDe) < 0 && periodo.isFechado()) {
				continue;
			}

//			if (periodo.getDataFinal().compareTo(dataAte) > 0) {
//				continue;
//			}

			periodo.setSaldoInicial(saldoInicial);

			for (Lancamento lancamento : periodo.getLancamentos()) {
				BigDecimal saldo = saldoInicial.add(lancamento.getValorLctoConvertido());
				lancamento.setSaldo(saldo);
				saldoInicial = saldo;
			}

		}
	}

	/****************************************************************************
	 * Metodo para montar os saldos dos lançamentos
	 ****************************************************************************/
	public void montaSaldo(Banco banco, Long idPeriodo) {
		Date inicio = banco.getPeriodo(idPeriodo).getDataInicio();
		Date fim = banco.getPeriodo(idPeriodo).getDataFinal();
		montaSaldo(banco, inicio, fim);
	}

	/****************************************************************************
	 * Metodo para montar os saldos dos lançamentos
	 ****************************************************************************/
	public void montaSaldo(Banco banco) {
		if (banco.getPeriodos().size() != 0) {
			int tam = banco.getPeriodos().size() - 1;
			Date inicio = banco.getPeriodos().get(0).getDataInicio();
			Date fim = banco.getPeriodos().get(tam).getDataFinal();
			montaSaldo(banco, inicio, fim);
		}
	}

	/****************************************************************************
	 * Metodo para retornar o periodo do banco a partir da data
	 ****************************************************************************/
	public BancoPeriodo selecionaPeriodo(Banco banco, Date data) {
		for (BancoPeriodo periodo : banco.getPeriodos()) {
			if (R42Data.dentroPeriodo(data, periodo)) {
				return periodo;
			}
		}
		return null;
	}

	/****************************************************************************
	 * Valida se o banco pode ser excluido
	 ****************************************************************************/
	public void validarSalvar(Banco banco) throws Exception {
		// Ajusta saldo inicial
		boolean fechado = true;
		for (BancoPeriodo periodo : banco.getPeriodos()) {
			if (periodo.isFechado()) {
				periodo.setAbertura(true);
				fechado = true;
			} else if (fechado) {
				periodo.setAbertura(true);
				fechado = false;
			} else {
				periodo.setAbertura(false);
				periodo.setSaldoInicial(new BigDecimal(0));
			}
		}

	}

	/****************************************************************************
	 * Valida se o banco pode ser excluido
	 ****************************************************************************/
	public void validarExclusao(Banco banco) throws Exception {
		if (banco.getPeriodos().size() != 0) {
			throw new Exception("Necessário excluir os periodos antes!");
		}
	}

	/****************************************************************************
	 * Registro numero do proximo arquivo
	 ****************************************************************************/
	public void proximoArquivo(Banco banco) {
		Long seqArquivo = banco.getSeqArquivo();
		if (seqArquivo == null) {
			seqArquivo = new Long(0);
		}
		seqArquivo++;
		banco.setSeqArquivo(seqArquivo);
		repBanco.save(banco);
	}
}

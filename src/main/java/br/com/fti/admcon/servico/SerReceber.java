package br.com.fti.admcon.servico;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.entidade.empresa.Boleto;
import br.com.fti.admcon.entidade.empresa.Cliente;
import br.com.fti.admcon.entidade.empresa.Receber;
import br.com.fti.admcon.repositorio.empresa.RepReceber;
import br.com.fti.admcon.entidade.empresa.Lancamento;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos a Receber:
 * 
 * @author Bob-Odin - 20/04/2017
 ****************************************************************************/
@Service
@Transactional
public class SerReceber {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepReceber repReceber;
	@Autowired
	SerLancamento serLancamento;

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Receber receber) throws Exception {
		try {
			validarSalvar(receber);
			repReceber.save(receber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar Lista
	 ****************************************************************************/
	public void salvar(List<Receber> listaReceber) throws Exception {
		try {
			for (Receber receber : listaReceber) {
				validarSalvar(receber);
			}
			repReceber.save(listaReceber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar antes de Salvar
	 ****************************************************************************/
	public void validarSalvar(Receber receber) throws Exception {
		try {

			BigDecimal totalBase = new BigDecimal(0);

			for (Lancamento lancamento : receber.getBaixas()) {
				// soma total base
				totalBase = totalBase.add(lancamento.getValorBase());
				// Valida o novo lancamento bancario
				if (lancamento.getIdLcto() == null) {
					serLancamento.validarSalvar(lancamento);
				}
			}

			// A soma do valor Base, não pode ser maior que o valor do título
			if (totalBase.compareTo(receber.getValor()) > 0) {
				throw new Exception("Soma valor base não pode ser maior que valor do título");
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Receber receber) throws Exception {
		try {
			validaExcluir(receber);
			repReceber.delete(receber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir uma lista de receber
	 ****************************************************************************/
	public void excluir(List<Receber> listaReceber) throws Exception {
		try {
			for (Receber receber : listaReceber) {
				validaExcluir(receber);
			}
			repReceber.delete(listaReceber);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar a exclusão de receber
	 ****************************************************************************/
	public void validaExcluir(Receber receber) throws Exception {
		// verifica se já existe baixa no titulo
		if (receber.getBaixas().size() != 0) {
			throw new Exception("o documento " + receber.getDocumento() + " já está com baixa!");
		}
	}

	/****************************************************************************
	 * Estornar baixa de titulos
	 ****************************************************************************/
	public void estornarBaixa(Receber receber, Long idBaixa) throws Exception {

		try {

			Lancamento lancamento = null;

			// Verifica se o idBaixa está na lista de baixa do receber
			boolean contem = false;
			for (Lancamento baixa : receber.getBaixas()) {
				if (baixa.getIdLcto() == idBaixa) {
					lancamento = baixa;
					contem = true;
					break;
				}
			}
			if (!contem) {
				throw new Exception("Id não está na lista de baixas");
			}

			// Verifica se o lançamento pode ser excluido
			serLancamento.validarExcluir(lancamento);
			receber.getBaixas().remove(lancamento);

			repReceber.save(receber);
			serLancamento.excluir(lancamento);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar lançamentos a receber por cliente
	 ****************************************************************************/
	public List<Receber> listarPorCliente(Cliente cliente) {
		return repReceber.findByCliente(cliente);
	}

	/****************************************************************************
	 * Metodo para Listar lançamentos a receber por boleto
	 ****************************************************************************/
	public List<Receber> listarPorBoleto(Boleto boleto) {
		return repReceber.findByBoleto(boleto);
	}

	/****************************************************************************
	 * Metodo para buscar Receber por Boleto e BoletoItem
	 ****************************************************************************/
	public Receber buscarPorBoletoEBoletoItem(Long idBoleto, Long idItem) {
		return repReceber.findByIdBoletoAndIdBoletoItem(idBoleto, idItem);
	}

}

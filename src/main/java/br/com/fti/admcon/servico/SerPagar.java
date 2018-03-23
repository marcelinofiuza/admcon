package br.com.fti.admcon.servico;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.entidade.empresa.Fornecedor;
import br.com.fti.admcon.entidade.empresa.Lancamento;
import br.com.fti.admcon.entidade.empresa.Pagar;
import br.com.fti.admcon.repositorio.empresa.RepPagar;

/****************************************************************************
 * Classe Serviço Regras de negócio dos Lançamentos a Pagar:
 * 
 * @author Thayro Rodrigues - 24/04/2017
 ****************************************************************************/
@Service
@Transactional
public class SerPagar {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepPagar repPagar;
	@Autowired
	SerLancamento serLancamento;
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Pagar pagar) throws Exception {
		try {
			validarSalvar(pagar);
			repPagar.save(pagar);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Validar antes de Salvar
	 ****************************************************************************/
	public void validarSalvar(Pagar pagar) throws Exception {
		try {
			
			BigDecimal totalBase = new BigDecimal(0);
			
			for (Lancamento lancamento : pagar.getBaixas()) {
				//soma total base
				totalBase = totalBase.add(lancamento.getValorBase());
				//Valida o novo lancamento bancario
				if(lancamento.getIdLcto() == null){
					serLancamento.validarSalvar(lancamento);
				}				
			}			
			
			//A soma do valor Base, não pode ser maior que o valor do título
			if(totalBase.compareTo(pagar.getValor()) > 0){
				throw new Exception("Soma valor base não pode ser maior que valor do título");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Pagar pagar) throws Exception {
		try {
			validaExcluir(pagar);
			repPagar.delete(pagar);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Validar antes da  exclusão
	 ****************************************************************************/
	public void validaExcluir(Pagar pagar) throws Exception {
		//verifica se já existe baixa no titulo
		if(pagar.getBaixas().size() != 0){
			throw new Exception("o documento "+pagar.getDocumento()+" já está com baixa!");
		}
	}
	
	/****************************************************************************
	 * Estornar baixa de titulos
	 ****************************************************************************/
	public void estornarBaixa(Pagar pagar, Long idBaixa) throws Exception {
		try {
			
			Lancamento lancamento = null;
			
			//Verifica se o idBaixa está na lista de baixa do receber
			boolean contem = false;
			for (Lancamento baixa : pagar.getBaixas()) {
				if(baixa.getIdLcto() == idBaixa){
					lancamento = baixa;					
					contem = true;
					break;
				}
			}
			if(!contem){
				throw new Exception("Id não está na lista de baixas");
			}
						
			//Verifica se o lançamento pode ser excluido
			serLancamento.validarExcluir(lancamento);			
			pagar.getBaixas().remove(lancamento);
			
			repPagar.save(pagar);
			serLancamento.excluir(lancamento);
						
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Listar lançamentos a pagar por fornecedor
	 ****************************************************************************/
	public List<Pagar> listarPorFornecedor(Fornecedor fornecedor){
		return repPagar.findByFornecedor(fornecedor);
	}
}

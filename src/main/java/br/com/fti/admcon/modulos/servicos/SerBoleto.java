package br.com.fti.admcon.modulos.servicos;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.modulos.entidades.empresa.Banco;
import br.com.fti.admcon.modulos.entidades.empresa.Boleto;
import br.com.fti.admcon.modulos.entidades.empresa.BoletoItem;
import br.com.fti.admcon.modulos.repositorio.empresa.RepBoleto;

/****************************************************************************
 * Classe Serviço Regras de negócio do Boleto Desenvolvido por :
 * 
 * @author Bob-Odin - 08/04/2017
 ****************************************************************************/
@Service
@Transactional
public class SerBoleto {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepBoleto repBoleto;

	/****************************************************************************
	 * Metodo para salvar o registro
	 ****************************************************************************/
	public void salvar(Boleto boleto) throws Exception {
		try {
			validaSalvar(boleto);			
			repBoleto.save(boleto);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Valida antes de salvar
	 ****************************************************************************/
	public void validaSalvar(Boleto boleto) throws Exception {

		if (boleto.getConta() == null || boleto.getConta().getIdConta() == null) {
			throw new Exception("Conta contábil é obrigatório");
		}

		if (boleto.getItens() == null || boleto.getItens().size() == 0) {
			throw new Exception("Nenhum item informado");
		}

		int i = 0;
		for (BoletoItem item : boleto.getItens()) {
			i++;
			if (item.getCliente() == null || item.getCliente().getIdCliente() == null) {
				throw new Exception("Cliente não informado no item " + i);
			}
			
			if (item.getTotalItem().compareTo(new BigDecimal(0)) == 0 ) {
				throw new Exception("Nenhum valor informado no item " + i);
			}	
			
			if(item.getVencimento() == null){
				throw new Exception("Data de vencimento não informada no item " + i);
			}
			
			if(item.getTotalItem().compareTo(new BigDecimal(0)) <= 0 ){
				throw new Exception("Valor zero no item " + i);
			}
		}
	}	
	
	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Boleto boleto) throws Exception {
		try {
			repBoleto.delete(boleto);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}	
	
	
	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Boleto> listarTodos() {
		return repBoleto.findAll();
	}

	/****************************************************************************
	 * Metodo para Listar os boletos pelo Banco
	 ****************************************************************************/
	public List<Boleto> listarPorBanco(Banco banco) {
		return repBoleto.findByBanco(banco);
	}
}

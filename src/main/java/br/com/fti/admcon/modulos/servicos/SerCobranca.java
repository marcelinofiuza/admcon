package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.modulos.entidades.empresa.Cobranca;
import br.com.fti.admcon.modulos.entidades.empresa.CobrancaItem;
import br.com.fti.admcon.modulos.repositorio.empresa.RepCobranca;


/****************************************************************************
 * Classe Serviço Regras de negócio do Cobranca Desenvolvido por :
 * 
 * @author Marcelino - 02/04/2017
 ****************************************************************************/
@Service
@Transactional
public class SerCobranca {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepCobranca repCobranca;

	/****************************************************************************
	 * Retorna se existe alguma cobrança ja cadastrada
	 ****************************************************************************/
	public boolean existeCobranca() {
		if (repCobranca.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Cobranca cobranca) throws Exception {
		try {
			validaSalvar(cobranca);
			repCobranca.save(cobranca);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Valida antes de salvar
	 ****************************************************************************/
	public void validaSalvar(Cobranca cobranca) throws Exception {

		if (cobranca.getConta() == null || cobranca.getConta().getIdConta() == null) {
			throw new Exception("Conta contábil é obrigatório");
		}

		if (cobranca.getItens() == null || cobranca.getItens().size() == 0) {
			throw new Exception("Nenhum item informado");
		}

		int i = 0;
		for (CobrancaItem item : cobranca.getItens()) {
			i++;
			if (item.getCliente() == null || item.getCliente().getIdCliente() == null) {
				throw new Exception("Cliente não informado no item " + i);
			}

		}

	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Cobranca cobranca) throws Exception {
		try {
			repCobranca.delete(cobranca);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Cobranca> listarTodos() {
		return repCobranca.findAll();
	}

}

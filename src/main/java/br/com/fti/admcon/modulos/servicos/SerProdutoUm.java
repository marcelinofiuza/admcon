package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.global.UnidadeMedida;
import br.com.fti.admcon.modulos.repositorio.global.RepProdutoUm;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Service
@Transactional
public class SerProdutoUm {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepProdutoUm repProdutoUm;
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(UnidadeMedida produtoUm) throws Exception {
		try {
		//	validarSalvar(produtoUm);
			repProdutoUm.save(produtoUm);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<UnidadeMedida> listarTodos() {
		return repProdutoUm.findAll();
	}
}
package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoUm;
import br.com.fti.admcon.modulos.repositorio.empresa.RepProdutoUm;

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
	public void salvar(ProdutoUm produtoUm) throws Exception {
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
	public List<ProdutoUm> listarTodos() {
		return repProdutoUm.findAll();
	}
}

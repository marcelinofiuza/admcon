package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.global.Categoria;
import br.com.fti.admcon.modulos.repositorio.global.RepProdutoCategoria;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 03/10/2019
 ****************************************************************************/
@Service
@Transactional
public class SerProdutoCategoria {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepProdutoCategoria repProdutoCategoria;
	
	
	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Categoria> listarTodos() {
		return repProdutoCategoria.findAll();
	}
	
}

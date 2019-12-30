package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoCategoria;
import br.com.fti.admcon.modulos.repositorio.empresa.RepProdutoCategoria;

/****************************************************************************
 * Classe Serviço Regras de negócio do Categoria Desenvolvido por :
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
	public List<ProdutoCategoria> listarTodos() {
		return repProdutoCategoria.findAll();
	}

	/****************************************************************************
	 * Salvar categorias
	 ****************************************************************************/
	public List<ProdutoCategoria> salvar(List<ProdutoCategoria> categorias) throws Exception {
		try {
			return repProdutoCategoria.save(categorias);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Excluir categorias
	 ****************************************************************************/
	public void excluir(ProdutoCategoria categoria)  throws Exception  {
		try {
			repProdutoCategoria.delete(categoria);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}

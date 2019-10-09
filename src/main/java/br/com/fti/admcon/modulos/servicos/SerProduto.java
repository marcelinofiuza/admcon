package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.repositorio.empresa.RepProduto;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 27/08/2019
 ****************************************************************************/
@Service
@Transactional
public class SerProduto {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepProduto repProduto;
	
	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Produto produto) throws Exception {
		try {
		//	validarSalvar(produto);
			repProduto.save(produto);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Produto> listarTodos() {
		return repProduto.findAll();
	}
	
	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Produto produto) throws Exception {
		try {
			repProduto.delete(produto);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}	
}

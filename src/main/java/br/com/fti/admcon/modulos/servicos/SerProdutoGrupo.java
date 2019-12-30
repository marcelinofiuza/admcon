package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoGrupo;
import br.com.fti.admcon.modulos.repositorio.empresa.RepProdutoGrupo;

/****************************************************************************
 * Classe Serviço Regras de negócio do Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 09/10/2019
 ****************************************************************************/
@Service
@Transactional
public class SerProdutoGrupo {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepProdutoGrupo repProdutoGrupo;

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<ProdutoGrupo> listarTodos() {
		return repProdutoGrupo.findAll();
	}

	/****************************************************************************
	 * Salvar grupos
	 ****************************************************************************/
	public List<ProdutoGrupo> salvar(List<ProdutoGrupo> grupos) throws Exception {
		try {
			return repProdutoGrupo.save(grupos);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/****************************************************************************
	 * Excluir grupo
	 ****************************************************************************/
	public void excluir(ProdutoGrupo grupo) throws Exception {
		try {
			repProdutoGrupo.delete(grupo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}



}

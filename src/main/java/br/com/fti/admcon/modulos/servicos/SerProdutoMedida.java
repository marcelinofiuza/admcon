package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoMedida;
import br.com.fti.admcon.modulos.repositorio.empresa.RepProdutoMedida;

/****************************************************************************
 * Classe Serviço Regras de negócio da Medida do produto Desenvolvido por :
 * 
 * @author Bob-Odin - 03/10/2019
 ****************************************************************************/
@Service
@Transactional
public class SerProdutoMedida {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepProdutoMedida repProdutoMedida;

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<ProdutoMedida> listarTodos() {
		return repProdutoMedida.findAll();
	}

	
	/****************************************************************************
	 * Salvar lista de unidades
	 ****************************************************************************/	
	public List<ProdutoMedida> salvar(List<ProdutoMedida> medidas) throws Exception {
		try {
			return repProdutoMedida.save(medidas);			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Excluir unidades
	 ****************************************************************************/	
	public void excluir(ProdutoMedida medida) throws Exception {
		try {
			repProdutoMedida.delete(medida);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}

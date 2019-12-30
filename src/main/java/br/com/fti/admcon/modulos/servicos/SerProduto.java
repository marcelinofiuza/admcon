package br.com.fti.admcon.modulos.servicos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.entidades.empresa.ProdutoComponente;
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

			produto = validaComponentes(produto);
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

	/****************************************************************************
	 * Metodo para Selecionar produto
	 ****************************************************************************/
	public Produto buscarPorId(Long idProduto) throws Exception {

		Produto produto;

		produto = repProduto.findOne(idProduto);

		if (produto == null) {
			throw new Exception("Produto " + idProduto + " não localizado!");
		}

		return produto;
	}

	/****************************************************************************
	 * Retornar uma lista de produtos filtrando por Like descrição
	 ****************************************************************************/
	public List<Produto> listarPorDescricao(String descricao) {
		return repProduto.findByDescricaoContaining(descricao);
	}

	/****************************************************************************
	 * Metodo para validar lista de componentes
	 ****************************************************************************/
	public Produto validaComponentes(Produto produto) throws Exception {

		List<ProdutoComponente> componentes = produto.getComponentes();
		Long idProduto = produto.getIdProduto();

		for (int i = 0; i < componentes.size(); i++) {

			Long idPrdCorrente = componentes.get(i).getItemProduto().getIdProduto();

			if (idPrdCorrente == null) {
				componentes.remove(i);
				continue;
			}

			if (idProduto != null) {
				if (idProduto == idPrdCorrente) {
					throw new Exception("Erro item " + (i + 1) + ". Produto não pode ser componente dele mesmo");
				}
			}

			try {
				buscarPorId(idPrdCorrente);
			} catch (Exception e) {
				throw new Exception("Erro item " + (i + 1) + ". " + e.getMessage());
			}

			if (componentes.get(i).getQtdUtilizada().compareTo(new BigDecimal(0)) == -1) {
				throw new Exception("Erro item " + (i + 1) + ". Quantidade utilizada deve ser maior que 0!");
			}

			for (int j = (i + 1); j < componentes.size(); j++) {

				Long idPrdAnalise = componentes.get(j).getItemProduto().getIdProduto();

				if (idPrdCorrente == idPrdAnalise) {
					throw new Exception("Produto duplicado item " + (i + 1) + " com item " + (j + 1));
				}
			}

			componentes.get(i).setProduto(produto);

		}

		produto.setComponentes(null);
		produto.setComponentes(componentes);

		return produto;
	}

	
}

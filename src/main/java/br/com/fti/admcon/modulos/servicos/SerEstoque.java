package br.com.fti.admcon.modulos.servicos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.modulos.entidades.empresa.EstoqueHeader;
import br.com.fti.admcon.modulos.entidades.empresa.EstoqueItem;
import br.com.fti.admcon.modulos.entidades.empresa.Produto;
import br.com.fti.admcon.modulos.repositorio.empresa.RepEstoque;
import br.com.fti.admcon.modulos.repositorio.empresa.RepProduto;

/****************************************************************************
 * Classe Serviço Regras de negócio do Estoque Desenvolvido por :
 * 
 * @author Bob-Odin - 26/02/2020
 ****************************************************************************/
@Service
@Transactional
public class SerEstoque {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepEstoque repEstoque;
	
	@Autowired
	RepProduto repProduto;

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<EstoqueHeader> listarTodos(String ordem) {

		List<EstoqueHeader> listaEstoqueHdr = new ArrayList<EstoqueHeader>();
		listaEstoqueHdr = repEstoque.findAll();

		if (!listaEstoqueHdr.isEmpty()) {

			// Ordena forma descendente
			if (ordem.equals("D")) {
				listaEstoqueHdr = bubbleSort(listaEstoqueHdr);
			}

		}

		return listaEstoqueHdr;

	}

	/****************************************************************************
	 * Metodo para salvar o registro
	 ****************************************************************************/
	public EstoqueHeader salvar(EstoqueHeader estoqueHeader) throws Exception {

		try {
			return repEstoque.save(estoqueHeader);
		} catch (Exception e) {
			throw new Exception("Erro ao salvar o registro!");
		}

	}

	/****************************************************************************
	 * Metodo para excluir o registro
	 ****************************************************************************/
	public void excluir(EstoqueHeader estoqueHeader) throws Exception {

		try {
			repEstoque.delete(estoqueHeader);
		} catch (Exception e) {
			throw new Exception("Erro ao excluir o registro!");
		}

	}

	/****************************************************************************
	 * Ordenação do periodo
	 ****************************************************************************/
	private List<EstoqueHeader> bubbleSort(final List<EstoqueHeader> listaEstoqueHdr) {
		int i;
		int j;
		for (i = listaEstoqueHdr.size() - 1; i > 0; i--) {
			// o maior valor entre vet[0] e vet[i] vai para a posição vet[i]
			EstoqueHeader estoqueAux;

			for (j = 0; j < i; j++) {
				if (listaEstoqueHdr.get(j).getDataInicio().before(listaEstoqueHdr.get(j + 1).getDataInicio())) {

					estoqueAux = listaEstoqueHdr.get(j);
					listaEstoqueHdr.set(j, listaEstoqueHdr.get(j + 1));
					listaEstoqueHdr.set(j + 1, estoqueAux);

				}
			}
		}
		return listaEstoqueHdr;
	}

	/****************************************************************************
	 * Retorna lista de Materiais com os valores de estoque
	 ****************************************************************************/	
	public List<EstoqueItem> montaEstoque(EstoqueHeader estoqueHeader) {
		
		List<EstoqueItem> estoqueItens = new ArrayList<>();
		List<Produto> produtos = new ArrayList<>();
		
		//Busca a lista de produtos cadastrados
		produtos = repProduto.findAll();
		
		
		for (Produto produto : produtos) {
			
			//Verifica se controla estoque
			if(produto.getCategoria().isEstoque()) {
				
				EstoqueItem estoqueItem;
				estoqueItem = calculaEstoque(produto, estoqueHeader.getDataFinal());
				estoqueItem.setEstoqueHeader(estoqueHeader);
				estoqueItens.add(estoqueItem);
				
			}
			
		}
		
		
		return estoqueItens;
	}
	
	/****************************************************************************
	 * Calcula estoque do item no periodo especificado
	 ****************************************************************************/		
	public EstoqueItem calculaEstoque(Produto produto, Date fim) {
		
		EstoqueItem estoqueItem = new EstoqueItem();
		estoqueItem.setProduto(produto);
		estoqueItem.setQuantidade(new BigDecimal(0));
		estoqueItem.setUnitario(new BigDecimal(0));
		
		//TODO: selecionar os lançamentos do ultimo periodo fechado até a data do perametro
		//fazer o calculo de entrada e saida, com base no FIFO (custo)
		
		
		return estoqueItem;
	}

}

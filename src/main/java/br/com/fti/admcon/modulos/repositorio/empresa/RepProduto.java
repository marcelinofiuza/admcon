package br.com.fti.admcon.modulos.repositorio.empresa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fti.admcon.modulos.entidades.empresa.Produto;

/****************************************************************************
 * Classe Repositório da entidade Cliente Desenvolvido por:
 * 
 * @author Bob-Odin - 04/11/2019
 ****************************************************************************/
public interface RepProduto extends JpaRepository<Produto, Long> {

	
	/****************************************************************************
	 * Retornar uma lista de produtos filtrando por Like descrição
	 ****************************************************************************/
	List<Produto> findByDescricaoContaining(String descricao);
	
}

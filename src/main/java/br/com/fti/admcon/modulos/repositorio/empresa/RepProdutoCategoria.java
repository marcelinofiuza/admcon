package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoCategoria;

/****************************************************************************
 * Classe Repositório para produto Categoria
 * 
 * @author Bob-Odin - 03/10/2019
 ****************************************************************************/
@Repository
public interface RepProdutoCategoria extends JpaRepository<ProdutoCategoria, Long> {

	
}

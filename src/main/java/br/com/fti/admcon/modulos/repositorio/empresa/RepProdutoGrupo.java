package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoGrupo;

/****************************************************************************
 * Classe Repositório para produto Categoria
 * 
 * @author Bob-Odin - 09/10/2019
 ****************************************************************************/
@Repository
public interface RepProdutoGrupo extends JpaRepository<ProdutoGrupo, Long> {

}

package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.ProdutoUm;

/****************************************************************************
 * Classe Repositório para produto UM
 * 
 * @author Bob-Odin - 25/08/2019
 ****************************************************************************/
@Repository
public interface RepProdutoUm extends JpaRepository<ProdutoUm, String> {

}

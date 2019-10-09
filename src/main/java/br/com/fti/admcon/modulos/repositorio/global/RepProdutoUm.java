package br.com.fti.admcon.modulos.repositorio.global;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.global.UnidadeMedida;

/****************************************************************************
 * Classe Repositório para produto UM
 * 
 * @author Bob-Odin - 25/08/2019
 ****************************************************************************/
@Repository
public interface RepProdutoUm extends JpaRepository<UnidadeMedida, Long> {

}

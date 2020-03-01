package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.EstoqueHeader;

/****************************************************************************
 * Classe Repositório da entidade Estoque_header e item:
 * 
 * @author Bob-Odin - 26/02/2020
 ****************************************************************************/
@Repository
public interface RepEstoque  extends JpaRepository<EstoqueHeader, Long> {

}

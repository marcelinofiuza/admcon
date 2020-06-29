package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.OcmHeader;

/****************************************************************************
 * Classe Repositório da entidade Orçamento Header e item:
 * 
 * @author Bob-Odin - 15/03/2020
 ****************************************************************************/
@Repository
public interface RepOrcamento extends JpaRepository<OcmHeader, Long>  {

}

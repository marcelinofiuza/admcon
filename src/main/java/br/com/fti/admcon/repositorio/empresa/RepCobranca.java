package br.com.fti.admcon.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.empresa.Cobranca;

/****************************************************************************
 * Classe Repositório da entidade Cobranca Desenvolvido por:
 * 
 * @author Bob-Odin - 02/04/2017
 ****************************************************************************/
@Repository
public interface RepCobranca extends JpaRepository<Cobranca, Long> {

}

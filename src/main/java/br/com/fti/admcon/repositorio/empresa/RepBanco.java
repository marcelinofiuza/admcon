package br.com.fti.admcon.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.empresa.Banco;

/****************************************************************************
 * Classe Repositório da entidade Banco Desenvolvido por:
 * 
 * @author Bob-Odin - 01/03/2017
 ****************************************************************************/
@Repository
public interface RepBanco extends JpaRepository<Banco, Long> {

}

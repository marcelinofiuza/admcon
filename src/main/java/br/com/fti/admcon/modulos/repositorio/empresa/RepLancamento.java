package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.Lancamento;



/****************************************************************************
 * Classe Repositório para Lançamentos bancários:
 * 
 * @author Bob-Odin - 22/03/2017
 ****************************************************************************/
@Repository
public interface RepLancamento extends JpaRepository<Lancamento, Long> {

}

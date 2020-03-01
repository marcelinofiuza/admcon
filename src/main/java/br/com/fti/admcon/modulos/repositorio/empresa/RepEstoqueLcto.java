package br.com.fti.admcon.modulos.repositorio.empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.empresa.EstoqueLancamento;

/****************************************************************************
 * Classe Repositório para Lançamentos de Estoque(Produto):
 * 
 * @author Bob-Odin - 29/02/2020
 ****************************************************************************/
@Repository
public interface RepEstoqueLcto extends JpaRepository<EstoqueLancamento, Long> {

}

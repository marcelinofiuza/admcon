package br.com.fti.admcon.repositorio.empresa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.empresa.Banco;
import br.com.fti.admcon.entidade.empresa.Boleto;

/****************************************************************************
 * Classe Repositório da entidade Boleto Desenvolvido por:
 * 
 * @author Bob-Odin - 07/04/2017
 ****************************************************************************/
@Repository
public interface RepBoleto extends JpaRepository<Boleto, Long> {

	/****************************************************************************
	 * Retornar a lista de Boletos pelo banco
	 ****************************************************************************/
	List<Boleto> findByBanco(Banco banco);

}

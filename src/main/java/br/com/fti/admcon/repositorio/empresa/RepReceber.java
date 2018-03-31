package br.com.fti.admcon.repositorio.empresa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.empresa.Boleto;
import br.com.fti.admcon.entidade.empresa.Cliente;
import br.com.fti.admcon.entidade.empresa.Receber;

/****************************************************************************
 * Classe Repositório para Lançamentos a receber
 * 
 * @author Bob-Odin - 20/04/2017
 ****************************************************************************/
@Repository
public interface RepReceber extends JpaRepository<Receber, Long> {

	/****************************************************************************
	 * Retornar uma lista de receber por cliente
	 ****************************************************************************/
	List<Receber> findByCliente(Cliente cliente);

	/****************************************************************************
	 * Retornar uma lista de receber por boleto
	 ****************************************************************************/
	List<Receber> findByBoleto(Boleto boleto);
		
	/****************************************************************************
	 * Busca o receber por idBoleto e idBoletoItem
	 ****************************************************************************/
	@Query(value="select * from receber where id_boleto = ?1 and id_item = ?2",nativeQuery=true)
	Receber findByIdBoletoAndIdBoletoItem(Long idBoleto, Long idItem);

	/****************************************************************************
	 * Busca os titulos pela data de lancamento e vencimento
	 ****************************************************************************/
	List<Receber> findByLancamentoBetweenAndVencimentoBetween(Date lctoDe, Date lctoAte, Date vencDe, Date vencAte);
	
	/****************************************************************************
	 * Busca os titulos pela data de lancamento e vencimento e cliente
	 ****************************************************************************/
	List<Receber> findByLancamentoBetweenAndVencimentoBetweenAndClienteIn
	(Date lctoDe, Date lctoAte, Date vencDe, Date vencAte, Collection<Cliente> cliente);
	
	
}

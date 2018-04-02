package br.com.fti.admcon.repositorio.empresa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.empresa.Fornecedor;
import br.com.fti.admcon.entidade.empresa.Pagar;

/****************************************************************************
 * Classe Repositório da entidade Pagar Desenvolvido por:
 * 
 * @author Thayro Rodrigues - 24/04/2017
 ****************************************************************************/

@Repository
public interface RepPagar extends JpaRepository<Pagar, Long> {

	/****************************************************************************
	 * Retornar uma lista de pagar por fornecedor
	 ****************************************************************************/
	List<Pagar> findByFornecedor(Fornecedor fornecedor);

	/****************************************************************************
	 * Busca os titulos pela data de lancamento e vencimento
	 ****************************************************************************/
	List<Pagar> findByLancamentoBetweenAndVencimentoBetween(Date lctoDe, Date lctoAte, Date vencDe, Date vencAte);

	/****************************************************************************
	 * Busca os titulos pela data de lancamento, vencimento e fornecedor
	 ****************************************************************************/
	List<Pagar> findByLancamentoBetweenAndVencimentoBetweenAndFornecedorIn
				(Date lctoDe, Date lctoAte, Date vencDe, Date vencAte, Collection<Fornecedor> fornecedores);

}

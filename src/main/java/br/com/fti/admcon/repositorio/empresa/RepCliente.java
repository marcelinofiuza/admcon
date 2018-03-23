package br.com.fti.admcon.repositorio.empresa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.empresa.Cliente;

/****************************************************************************
 * Classe Repositório da entidade Cliente Desenvolvido por:
 * 
 * @author Gustavo - 30/03/2017
 ****************************************************************************/
@Repository
public interface RepCliente extends JpaRepository<Cliente, Long> {

	/****************************************************************************
	 * Retornar uma lista de clientes filtrando por Like RazaoSocial e Unidade
	 ****************************************************************************/
	List<Cliente> findByRazaoSocialContainingAndUnidadeContaining(String razaoSocial, String unidade);

	/****************************************************************************
	 * Retornar um cliente fazendo busca pela unidade
	 ****************************************************************************/		
	Cliente findByUnidade(String unidade);
	
}

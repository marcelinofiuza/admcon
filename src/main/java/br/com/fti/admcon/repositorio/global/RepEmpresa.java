package br.com.fti.admcon.repositorio.global;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.entidade.global.Empresa;

/****************************************************************************
 * Classe Repositório da entidade Empresa Desenvolvido por :
 * 
 * @author Bob-Odin - 01/02/2017
 ****************************************************************************/
@Repository
public interface RepEmpresa extends JpaRepository<Empresa, Long> {

	/****************************************************************************
	 * Retornar uma lista de empresas filtrando por Like RazaoSocial e Fantasia
	 ****************************************************************************/
	public List<Empresa> findByRazaoSocialContainingAndFantasiaContaining(String razaoSocial, String fantasia);

}

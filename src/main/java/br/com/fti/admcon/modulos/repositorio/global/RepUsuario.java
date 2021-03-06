package br.com.fti.admcon.modulos.repositorio.global;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fti.admcon.modulos.entidades.global.Usuario;

/****************************************************************************
 * Classe Repositório da entidade Usuário Desenvolvido por:
 * 
 * @author Bob-Odin - 30/01/2017
 ****************************************************************************/
@Repository
public interface RepUsuario extends JpaRepository<Usuario, Long> {

	/****************************************************************************
	 * Retornar o usuario pela Credencial
	 ****************************************************************************/
	public Usuario findByCredencial(String credencial);

}

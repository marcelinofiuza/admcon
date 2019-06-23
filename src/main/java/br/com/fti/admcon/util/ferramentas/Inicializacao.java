package br.com.fti.admcon.util.ferramentas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fti.admcon.enums.Role;
import br.com.fti.admcon.modulos.entidades.global.Usuario;
import br.com.fti.admcon.modulos.entidades.global.UsuarioRoles;
import br.com.fti.admcon.modulos.servicos.SerUsuario;

/****************************************************************************
 * Classe de Inicialização (executa processos quando o sistema é inicializado)
 * 
 * @author: Bob-Odin - 30/01/2017
 ****************************************************************************/
@Component
public class Inicializacao {

	@Autowired
	SerUsuario serUsuario;

	@EventListener
	public void usuarioPadrao(ApplicationReadyEvent event) {

		Usuario usuario = serUsuario.buscarPorCredencial("ADMCON");

		if (usuario == null) {
			try {
				serUsuario.salvar(gerarUsuario());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Usuario gerarUsuario() {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		UsuarioRoles roles = new UsuarioRoles();
		roles.setRole(Role.ADMIN);

		Usuario usuario = new Usuario();
		usuario.setCredencial("JEDI");
		usuario.setSenha(encoder.encode("#42"));
		usuario.setNome("Jedi 42");
		usuario.setBloqueado(false);
		usuario.addRole(roles);

		return usuario;
	}
}

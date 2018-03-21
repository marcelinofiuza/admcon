package br.com.fti.admcon.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.entidade.global.Usuario;
import br.com.fti.admcon.repositorio.global.RepUsuario;

/****************************************************************************
 * Classe Serviço Regras de negócio do Usuário Desenvolvido por :
 * 
 * @author Bob-Odin - 30/01/2017
 ****************************************************************************/
@Service
public class SerUsuario {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepUsuario repUsuario;

	/****************************************************************************
	 * Validar e salvar o Usuário
	 ****************************************************************************/
	public void salvar(Usuario usuario) throws Exception {
		try {					
			repUsuario.save(usuario);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar e excluir o Usuário
	 ****************************************************************************/
	public void excluir(Usuario usuario) throws Exception {
		try {
			repUsuario.delete(usuario);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Retorna lista de todos os usuários
	 ****************************************************************************/
	public List<Usuario> listarTodos() {
		return repUsuario.findAll();
	}

	/****************************************************************************
	 * Retorna o Usuário pela credencial
	 ****************************************************************************/
	public Usuario buscarPorCredencial(String credencial) {
		return repUsuario.findByCredencial(credencial);
	}

}

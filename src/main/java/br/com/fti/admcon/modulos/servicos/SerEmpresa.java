package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.modulos.entidades.global.Empresa;
import br.com.fti.admcon.modulos.repositorio.global.RepEmpresa;


/****************************************************************************
 * Classe Serviço Regras de negócio da Empresa Desenvolvido por :
 * 
 * @author Bob-Odin - 01/02/2017
 ****************************************************************************/
@Service
public class SerEmpresa {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepEmpresa repEmpresa;

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Empresa Empresa) throws Exception {
		try {
			ajustarDados(Empresa);
			repEmpresa.save(Empresa);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Empresa Empresa) throws Exception {
		try {
			repEmpresa.delete(Empresa);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Buscar empresa pelo ID
	 ****************************************************************************/
	public Empresa buscarPorId(Long idEmpresa) {
		return repEmpresa.findOne(idEmpresa);
	}
	
	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Empresa> listarTodos() {
		return repEmpresa.findAll();
	}

	/****************************************************************************
	 * Retornar uma lista de empresas filtrando por Like RazaoSocial e Fantasia
	 ****************************************************************************/
	public List<Empresa> listarPorRazaoSocialOuFantasia(String razaoSocial, String fantasia) {
		return repEmpresa.findByRazaoSocialContainingAndFantasiaContaining(razaoSocial, fantasia);
	}

	/****************************************************************************
	 * Metodo para validar dados antes de salvar
	 ****************************************************************************/
	private void ajustarDados(Empresa empresa) {
		
		// Seta null no cnpj quando estiver em branco para validação
		if (empresa.getCnpj() != null && empresa.getCnpj().isEmpty()) {
			empresa.setCnpj(null);
		}

		// Seta null no cpf quando estiver em branco para validação
		if (empresa.getCpf() !=null && empresa.getCpf().isEmpty()) {
			empresa.setCpf(null);
		}

	}
}

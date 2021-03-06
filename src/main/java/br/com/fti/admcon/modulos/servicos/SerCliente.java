package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.modulos.entidades.empresa.Cliente;
import br.com.fti.admcon.modulos.repositorio.empresa.RepCliente;

/****************************************************************************
 * Classe Serviço Regras de negócio do Cliente Desenvolvido por :
 * 
 * @author Gustavo - 30/03/2017
 ****************************************************************************/
@Service
@Transactional
public class SerCliente {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepCliente repCliente;

	/****************************************************************************
	 * Retorna se existe algum cliente cadastro
	 ****************************************************************************/
	public boolean existeCliente() {
		if (repCliente.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/****************************************************************************
	 * Metodo para Validar e salvar
	 ****************************************************************************/
	public void salvar(Cliente cliente) throws Exception {
		try {
			ajustarDados(cliente);
			repCliente.save(cliente);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Validar e excluir
	 ****************************************************************************/
	public void excluir(Cliente cliente) throws Exception {
		try {
			repCliente.delete(cliente);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public Cliente buscarPorId(Long id) {
		return repCliente.findOne(id);
	}

	/****************************************************************************
	 * Metodo para Listar todos os registros
	 ****************************************************************************/
	public List<Cliente> listarTodos() {
		return repCliente.findAll();
	}

	/****************************************************************************
	 * Metodo para validar dados antes de salvar
	 ****************************************************************************/
	private void ajustarDados(Cliente cliente) {

		// Seta null no cnpj quando estiver em branco para validação
		if (cliente.getCnpj() != null && cliente.getCnpj().isEmpty()) {
			cliente.setCnpj(null);
		}

		// Seta null no cpf quando estiver em branco para validação
		if (cliente.getCpf() != null && cliente.getCpf().isEmpty()) {
			cliente.setCpf(null);
		}
		
		if (cliente.getFantasia() == null) {
			cliente.setFantasia(cliente.getRazaoSocial());
		}

	}

	/****************************************************************************
	 * Retornar uma lista de clientes filtrando por Like RazaoSocial e Unidade
	 ****************************************************************************/
	public List<Cliente> listarPorRazaoSocialOuUnidade(String razaoSocial, String unidade) {
		return repCliente.findByRazaoSocialContainingAndUnidadeContaining(razaoSocial, unidade);
	}

	/****************************************************************************
	 * Retornar um cliente buscando pela unidade
	 ****************************************************************************/
	public Cliente buscarPorUnidade(String unidade) {
		return repCliente.findByUnidade(unidade);
	}

}

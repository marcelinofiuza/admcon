package br.com.fti.admcon.relatorio.relCliente;

import java.util.ArrayList;
import java.util.List;

import br.com.fti.admcon.entidade.empresa.Cliente;
import br.com.fti.admcon.entidade.empresa.ClienteContatos;
import br.com.fti.admcon.relatorio.Relatorio;
import br.com.fti.admcon.util.R42Util;


/****************************************************************************
 * Relatórido Lista de Clientes
 * 
 * @author: Bob-Odin - 07/06/2017
 ****************************************************************************/
public class RelCliente {

	public Relatorio relatorio;
	public List<EstruturaRelCliente> eRelClientes;

	/****************************************************************************
	 * Construtor
	 ****************************************************************************/
	public RelCliente() {
		relatorio = new Relatorio();
	}

	/****************************************************************************
	 * Seta lista de Clientes a ser impresso
	 ****************************************************************************/
	public void setLista(List<Cliente> listaClientes) {

		eRelClientes = new ArrayList<>();
		
		for (Cliente cliente : listaClientes) {
			eRelClientes.add(converteDados(cliente));
		}

		relatorio.setListaDados(eRelClientes);
	}

	/****************************************************************************
	 * Converte dados para impressão
	 ****************************************************************************/
	public EstruturaRelCliente converteDados(Cliente cliente) {
		
		EstruturaRelCliente eRelCliente = new EstruturaRelCliente();

		eRelCliente.setIdCliente(cliente.getIdCliente());
		eRelCliente.setUnidade(cliente.getUnidade());
		eRelCliente.setRazaoSocial(cliente.getRazaoSocial());
		eRelCliente.setCnpj(cliente.getCnpj());
		eRelCliente.setCpf(cliente.getCpf());
		eRelCliente.setCep(cliente.getEndereco().getCep());
		eRelCliente.setEndereco(cliente.getEndereco().getEnderecoCompleto());
		eRelCliente.setCidade(cliente.getEndereco().getCidade());
		eRelCliente.setUf(cliente.getEndereco().getUf().toString());

		if (cliente.getContatos() != null && !cliente.getContatos().isEmpty()) {

			ClienteContatos contato = cliente.getContatos().get(0);

			eRelCliente.setNome(contato.getContato().getNomeContato());
			eRelCliente.setEmail(contato.getContato().getEmail());
			eRelCliente.setFixo(contato.getContato().getTelefone() + "#" + contato.getContato().getRamal());
			eRelCliente.setCelular(contato.getContato().getCelular());

		}
		
		return eRelCliente;
	}

	/****************************************************************************
	 * Gera relatório de clientes
	 ****************************************************************************/
	public void gerar() throws Exception {
		relatorio.setArquivoJrxml("Clientes");
		relatorio.setParametro("empresa", R42Util.resgataEmpresa().getFantasia());
		relatorio.geraRelatorio();
	}

}

package br.com.fti.admcon.util.migracao;

import org.jamel.dbf.DbfReader;
import org.jamel.dbf.utils.DbfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fti.admcon.modulos.entidades.embeddeble.Endereco;
import br.com.fti.admcon.modulos.entidades.empresa.Cliente;
import br.com.fti.admcon.modulos.entidades.empresa.Conta;
import br.com.fti.admcon.modulos.servicos.SerCliente;
import br.com.fti.admcon.modulos.servicos.SerConta;
import br.com.fti.admcon.util.ferramentas.R42Util;

/****************************************************************************
 * Classe para migrar dados dos Clientes ADMCON -> MARVIN
 * 
 * @author Bob-Odin - 02/04/2017
 ****************************************************************************/
@Component
public class MigrarCliente {

	private final String arquivo = "DCL01.DBF";
	private DbfReader dbfReader;

	@Autowired
	SerConta serConta;
	@Autowired
	SerCliente serCliente;

	/****************************************************************************
	 * Executa a migração do arquivo DCL01.DBF para Entidade Cliente
	 ****************************************************************************/
	public void executar() throws Exception {

		dbfReader = R42Util.lerDbf(arquivo);

		for (int i = 0; i < dbfReader.getRecordCount(); i++) {
			Object[] row = dbfReader.nextRecord();
			try {
				String clcodigo = new String(DbfUtils.trimLeftSpaces((byte[]) row[0]));
				String clrazao = new String(DbfUtils.trimLeftSpaces((byte[]) row[1]));
//				String clfone	= new String(DbfUtils.trimLeftSpaces((byte[]) row[2]));
//				String clfax	= new String(DbfUtils.trimLeftSpaces((byte[]) row[3]));
				String clinest = new String(DbfUtils.trimLeftSpaces((byte[]) row[4]));
				String clcgc = new String(DbfUtils.trimLeftSpaces((byte[]) row[5]));
				String clender = new String(DbfUtils.trimLeftSpaces((byte[]) row[6]));
				String clbair = new String(DbfUtils.trimLeftSpaces((byte[]) row[7]));
				String clcep = new String(DbfUtils.trimLeftSpaces((byte[]) row[8]));
				String clcidade = new String(DbfUtils.trimLeftSpaces((byte[]) row[9]));
				String cluf = new String(DbfUtils.trimLeftSpaces((byte[]) row[10]));
//				String clcend	= new String(DbfUtils.trimLeftSpaces((byte[]) row[11]));
//				String clcbai	= new String(DbfUtils.trimLeftSpaces((byte[]) row[12]));
//				String clccep	= new String(DbfUtils.trimLeftSpaces((byte[]) row[13]));
//				String clccid	= new String(DbfUtils.trimLeftSpaces((byte[]) row[14]));
//				String clcuf	= new String(DbfUtils.trimLeftSpaces((byte[]) row[15]));
//				String inrazao	= new String(DbfUtils.trimLeftSpaces((byte[]) row[16]));
//				String infone	= new String(DbfUtils.trimLeftSpaces((byte[]) row[17]));
//				String infax	= new String(DbfUtils.trimLeftSpaces((byte[]) row[18]));
//				String ininest	= new String(DbfUtils.trimLeftSpaces((byte[]) row[19]));
//				String incgc	= new String(DbfUtils.trimLeftSpaces((byte[]) row[20]));
//				String inender	= new String(DbfUtils.trimLeftSpaces((byte[]) row[21]));
//				String inbair	= new String(DbfUtils.trimLeftSpaces((byte[]) row[22]));
//				String incep	= new String(DbfUtils.trimLeftSpaces((byte[]) row[23]));
//				String incidade	= new String(DbfUtils.trimLeftSpaces((byte[]) row[24]));
//				String inuf		= new String(DbfUtils.trimLeftSpaces((byte[]) row[25]));
//				String incend	= new String(DbfUtils.trimLeftSpaces((byte[]) row[26]));
//				String incbai	= new String(DbfUtils.trimLeftSpaces((byte[]) row[27]));
//				String inccep	= new String(DbfUtils.trimLeftSpaces((byte[]) row[28]));
//				String inccid	= new String(DbfUtils.trimLeftSpaces((byte[]) row[29]));
//				String incuf	= new String(DbfUtils.trimLeftSpaces((byte[]) row[30]));				
//				String cldest1	= new String(DbfUtils.trimLeftSpaces((byte[]) row[31]));	
//				String cldest2	= new String(DbfUtils.trimLeftSpaces((byte[]) row[32]));
//				String cldest3	= new String(DbfUtils.trimLeftSpaces((byte[]) row[33]));
//				String cldest4	= new String(DbfUtils.trimLeftSpaces((byte[]) row[34]));				
				String clconta = row[35].toString();
//				String clfracao	  = new String(DbfUtils.trimLeftSpaces((byte[]) row[36]));
//				String clfracao2  = new String(DbfUtils.trimLeftSpaces((byte[]) row[37]));	
//				String clfracao3  = new String(DbfUtils.trimLeftSpaces((byte[]) row[38]));	
//				String clfracao4  = new String(DbfUtils.trimLeftSpaces((byte[]) row[39]));	
//				String clgrupo    = new String(DbfUtils.trimLeftSpaces((byte[]) row[40]));	
//				String clgerlan   = new String(DbfUtils.trimLeftSpaces((byte[]) row[41]));	
//				String cldesconto = new String(DbfUtils.trimLeftSpaces((byte[]) row[42]));	
//				String cldiapag   = new String(DbfUtils.trimLeftSpaces((byte[]) row[43]));	
//				String cldttitabe = new String(DbfUtils.trimLeftSpaces((byte[]) row[44]));

				String logradouro = "";
				String numero = "";
				String complemento = "";
				String[] parts = clender.split(",");
				if (parts.length >= 1) {
					logradouro = parts[0].trim();
				}
				if (parts.length > 1) {
					numero = parts[1].trim();
					if (numero.length() > 10) {
						numero.substring(0, 10);
					}
				}
				if (parts.length > 2) {
					complemento = parts[2].trim();
					if (complemento.length() > 20) {
						complemento.substring(0, 20);
					}
				}

				Endereco endereco = new Endereco();
				endereco.setCep(clcep);
				endereco.setTipoLogradouro("");
				endereco.setLogradouro(logradouro);
				endereco.setNumero(numero);
				endereco.setComplemento(complemento);
				endereco.setBairro(clbair);
				endereco.setCidade(clcidade);
				endereco.setUf(R42Util.converteEstado(cluf));

//				Contato contato = new Contato();
//				contato.setNomeContato(clrazao);
//				contato.setTelefone(clfone);

				Cliente cliente = new Cliente();
				cliente.setRazaoSocial(clrazao);
				cliente.setFantasia(clrazao);
				cliente.setCnpj(R42Util.validaCNPJ(clcgc, true));
				cliente.setCpf(R42Util.validaCPF(clcgc, true));
				cliente.setInsEstadual(clinest);
				cliente.setUnidade(clcodigo);
				cliente.setEndereco(endereco);
//				cliente.addContato(contato);
				cliente.setConta(converteConta(clconta));

				serCliente.salvar(cliente);
			} catch (Exception e) {
				// TODO: handle exception
				throw new Exception(e.getMessage());
			}
		}

	}

	/****************************************************************************
	 * Converte String em Conta (busca no banco de dados)
	 ****************************************************************************/
	private Conta converteConta(String reduzida) {
		Conta conta = serConta.buscarPorReduzida(reduzida);
		return conta;
	}

}

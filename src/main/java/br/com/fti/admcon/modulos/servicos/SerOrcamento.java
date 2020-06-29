package br.com.fti.admcon.modulos.servicos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fti.admcon.modulos.entidades.empresa.OcmHeader;
import br.com.fti.admcon.modulos.repositorio.empresa.RepOrcamento;

/****************************************************************************
 * Classe Serviço Regras de negócio da criação de Orçamentos
 * 
 * @author Bob-Odin - 23/03/2020
 ****************************************************************************/
@Service
@Transactional
public class SerOrcamento {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepOrcamento repOrcamento;	
	
	
	
	/****************************************************************************
	 * Listar Orçamento
	 ****************************************************************************/	
	public List<OcmHeader> listar(){
		return repOrcamento.findAll();	
	}
	
	
}

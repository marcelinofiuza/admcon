package br.com.fti.admcon.controleTela.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.SortEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.modulos.entidades.empresa.Cliente;
import br.com.fti.admcon.modulos.entidades.empresa.Receber;
import br.com.fti.admcon.modulos.servicos.SerReceber;
import br.com.fti.admcon.util.ferramentas.FacesMessages;

/****************************************************************************
 * Classe controle para relatorio de receber
 * 
 * @author: Bob-Odin - 08/06/2017
 ****************************************************************************/
@Named
@ManagedBean
public class ControleRelReceber implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/

	private static final long serialVersionUID = 1L;

	@Autowired
	SerReceber serReceber;

	@Autowired
	FacesMessages mensagens;

	private Date lctoDe;
	private Date lctoAte;
	private Date vctoDe;
	private Date vctoAte;

	private BigDecimal somaValor;
	private BigDecimal somaAcrescimos;
	private BigDecimal somaDescontos;
	private BigDecimal somaRecebido;

	private String status = "T";
	
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private List<Receber> lancamentos = new ArrayList<Receber>();

	/* sem get e set - uso interno */
	private String sortColumnCase = "VCTO";

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	public void iniciar() {
		clientes = new ArrayList<Cliente>();
		lancamentos = new ArrayList<Receber>();
		RequestContext.getCurrentInstance().execute("PF('wgSelecao').show();");
	}

	/****************************************************************************
	 * Pesquisar
	 ****************************************************************************/
	public void pesquisar() {

		lancamentos = new ArrayList<Receber>();
		List<Receber> lctos = new ArrayList<Receber>();

		if (lctoDe != null || lctoAte != null || vctoDe != null || vctoAte != null || !clientes.isEmpty()) {
			lctos = serReceber.listarTitulos(lctoDe, lctoAte, vctoDe, vctoAte, clientes);
		} else {
			mensagens.warning("Nenhum parâmetro informado");
		}

		for (Receber receber : lctos) {

			if (status.equalsIgnoreCase("A") && !receber.isQuitado()) {
				lancamentos.add(receber);
			} else if (status.equalsIgnoreCase("B") && receber.isQuitado()) {
				lancamentos.add(receber);
			} else if (status.equalsIgnoreCase("T")){
				lancamentos.add(receber);
			}

		}

		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
	}

	/****************************************************************************
	 * Resgata lista de clientes selecionados
	 ****************************************************************************/
	public void retListaClientes(SelectEvent event) {

		clientes = new ArrayList<Cliente>();
		@SuppressWarnings("unchecked")
		List<Object> object = (List<Object>) event.getObject();

		for (final Object object2 : object) {
			clientes.add((Cliente) object2);
		}

	}

	/****************************************************************************
	 * Determinar coluna de ordenação
	 ****************************************************************************/
	public void onSort(SortEvent event) {
		sortColumnCase = event.getSortColumn().getHeaderText();
	}

	/****************************************************************************
	 * Soma os valores por colunas
	 ****************************************************************************/
	public void somarTotal(Object filter) {

		this.somaValor = new BigDecimal(0);
		this.somaAcrescimos = new BigDecimal(0);
		this.somaDescontos = new BigDecimal(0);
		this.somaRecebido = new BigDecimal(0);

		for (Receber lancamento : lancamentos) {

			if (sortColumnCase.equalsIgnoreCase("UNID.")) {
				if (lancamento.getCliente().getUnidade().equalsIgnoreCase(filter.toString())) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("CLIENTE")) {
				if (lancamento.getCliente().getFantasia().equalsIgnoreCase(filter.toString())) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("LCTO")) {
				if (lancamento.getLancamento().equals(filter)) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("DOCUMENTO")) {
				if (lancamento.getDocumento().equalsIgnoreCase(filter.toString())) {
					somaValores(lancamento);
				}
			} else if (sortColumnCase.equalsIgnoreCase("VCTO")) {
				if (lancamento.getVencimento().equals(filter)) {
					somaValores(lancamento);
				}
			}
		}
	}

	/****************************************************************************
	 * Soma os valores
	 ****************************************************************************/	
	private void somaValores(Receber lancamento) {
		this.somaValor = this.somaValor.add(lancamento.getValor());
		this.somaAcrescimos = this.somaAcrescimos.add(lancamento.getAcrescimos());
		this.somaDescontos = this.somaDescontos.add(lancamento.getDescontos());
		this.somaRecebido = this.somaRecebido.add(lancamento.getRecebido());
	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/
	public List<Receber> getLancamentos() {
		return lancamentos;
	}

	public Date getLctoDe() {
		return lctoDe;
	}

	public void setLctoDe(Date lctoDe) {
		this.lctoDe = lctoDe;
	}

	public Date getLctoAte() {
		return lctoAte;
	}

	public void setLctoAte(Date lctoAte) {
		this.lctoAte = lctoAte;
	}

	public Date getVctoDe() {
		return vctoDe;
	}

	public void setVctoDe(Date vctoDe) {
		this.vctoDe = vctoDe;
	}

	public Date getVctoAte() {
		return vctoAte;
	}

	public void setVctoAte(Date vctoAte) {
		this.vctoAte = vctoAte;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public int getTotalClientes() {
		if (clientes == null || clientes.isEmpty()) {
			return 0;
		} else {
			return clientes.size();
		}
	}

	public BigDecimal getSomaValor() {
		return somaValor;
	}

	public BigDecimal getSomaAcrescimos() {
		return somaAcrescimos;
	}

	public BigDecimal getSomaDescontos() {
		return somaDescontos;
	}

	public BigDecimal getSomaRecebido() {
		return somaRecebido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

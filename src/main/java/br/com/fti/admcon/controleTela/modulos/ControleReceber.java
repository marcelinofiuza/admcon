package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fti.admcon.enums.DebitoCredito;
import br.com.fti.admcon.enums.OrigemLcto;
import br.com.fti.admcon.modulos.entidades.empresa.Banco;
import br.com.fti.admcon.modulos.entidades.empresa.BancoPeriodo;
import br.com.fti.admcon.modulos.entidades.empresa.Cliente;
import br.com.fti.admcon.modulos.entidades.empresa.Conta;
import br.com.fti.admcon.modulos.entidades.empresa.Lancamento;
import br.com.fti.admcon.modulos.entidades.empresa.Receber;
import br.com.fti.admcon.modulos.servicos.SerBanco;
import br.com.fti.admcon.modulos.servicos.SerReceber;
import br.com.fti.admcon.util.ferramentas.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Lançamento a Receber
 * 
 * @author: Bob-Odin - 20/04/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleReceber implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private List<Receber> listaReceber = new ArrayList<Receber>();
	private List<Lancamento> baixas = new ArrayList<Lancamento>();
	private Receber receber = new Receber();
	private Receber receberSelect;
	private Cliente cliente;
	private Conta conta;
	private Lancamento lancamento = new Lancamento();
	private Banco banco;

	private boolean salvarTitulo = true;
	
	@Autowired
	SerReceber serReceber;
	@Autowired
	SerBanco serBanco;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {
		RequestContext.getCurrentInstance().execute("PF('wgSelecaoCliente').show();");
	}

	/****************************************************************************
	 * Salvar o lançamento a receber
	 ****************************************************************************/
	public void salvar() {
		try {
			receber.setConta(conta);
			serReceber.salvar(receber);
			confirmaCliente();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			receberSelect = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Novo o lançamento a receber
	 ****************************************************************************/
	public void novoLancamento() {	
		salvarTitulo = true;
		conta = new Conta();
		receber = new Receber();
		receber.setCliente(cliente);
		receber.setLancamento(new Date());
		receber.setValor(new BigDecimal(0));
		receberSelect = null;
		baixas = new ArrayList<Lancamento>();
	}

	/****************************************************************************
	 * Novo o lançamento a receber
	 ****************************************************************************/
	public void editLancamento() {
		salvarTitulo = true;
		receber = receberSelect;
		conta = receberSelect.getConta();
		baixas = receberSelect.getBaixas();
		if (receber.getBoleto() != null) {
			salvarTitulo = false;
			mensagens.warning("Título gerado por boleto!");
		}
		if (receber.getBaixas() != null && !receber.getBaixas().isEmpty()) {
			salvarTitulo = false;
			mensagens.warning("Título já está com baixa!");
		}		
	}

	/****************************************************************************
	 * Excluir registro selecionado
	 ****************************************************************************/
	public void excluir() {
		try {
			if (receberSelect.getBoleto() == null) {
				serReceber.excluir(receberSelect);
				confirmaCliente();
				mensagens.info("Registro excluido com sucesso!");
			} else {
				mensagens.error("Título deve ser estornado pelo Boleto!");
			}
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		receberSelect = null;
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Resgata o Cliente selecionado no dialogo
	 ****************************************************************************/
	public void clienteSelecionado(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	/****************************************************************************
	 * Efetua a confirmação do cliente selecionado, faz a busca das duplicatas
	 ****************************************************************************/
	public void confirmaCliente() {
		if (cliente != null) {
			listaReceber = serReceber.listarTitulos(cliente);
			RequestContext.getCurrentInstance().execute("PF('wgSelecaoCliente').hide();");
		}
	}

	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = (Conta) event.getObject();
	}

	/****************************************************************************
	 * Preparar Nova baixa
	 ****************************************************************************/
	public void novaBaixa() {
		lancamento = new Lancamento();
		lancamento.setReceber(receberSelect);
		banco = new Banco();		
		if(receberSelect.getBoleto() != null){
			banco = receberSelect.getBoleto().getBanco();
		}
		String historico = "RECEBIMENTO " +
							receberSelect.getCliente().getRazaoSocial() +
							" DUPLICATA " +
							receberSelect.getDocumento();
		
		lancamento.setDataLcto(new Date());
		lancamento.setValorBase(receberSelect.getSaldo());
		lancamento.setHistorico(historico);
	}
	
	/****************************************************************************
	 * Salvar a baixa lançamento a receber
	 ****************************************************************************/
	public void salvarBaixa() {
		try {			
			BancoPeriodo periodo = serBanco.selecionaPeriodo(banco, lancamento.getDataLcto());
			if(periodo != null){		
				
				lancamento.setBancoPeriodo(periodo);
				lancamento.setConta(receberSelect.getCliente().getConta());
				lancamento.setOrigemLcto(OrigemLcto.DCR);
				lancamento.setTipoLcto(DebitoCredito.CREDITO);
								
				receberSelect.addBaixa(lancamento);
				serReceber.salvar(receberSelect);
				
				confirmaCliente();
				receberSelect = null;
				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			}else{
				FacesContext.getCurrentInstance().validationFailed();
				mensagens.error("Banco sem periodo aberto para o lançamento!");				
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}		
	}

	/****************************************************************************
	 * Resgata o Banco selecionado no dialogo
	 ****************************************************************************/
	public void bancoSelecionado(SelectEvent event) {
		banco = (Banco) event.getObject();
	}
	
	/****************************************************************************
	 * Estorna baixa do titulo
	 ****************************************************************************/	
	public void estornarBaixa(Lancamento baixa){
		try {			
			serReceber.estornarBaixa(receberSelect, baixa.getIdLcto());	
			receberSelect = null;

			confirmaCliente();
			mensagens.info("Estorno efetuado com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			RequestContext.getCurrentInstance().execute("PF('wgDadosLancamento').hide();");
					
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}		
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Receber> getListaReceber() {
		return listaReceber;
	}

	public void setListaReceber(List<Receber> listaReceber) {
		this.listaReceber = listaReceber;
	}

	public Receber getReceber() {
		return receber;
	}

	public void setReceber(Receber receber) {
		this.receber = receber;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Receber getReceberSelect() {
		return receberSelect;
	}

	public void setReceberSelect(Receber receberSelect) {
		this.receberSelect = receberSelect;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public boolean isSalvarTitulo() {
		return salvarTitulo;
	}

	public List<Lancamento> getBaixas() {
		return baixas;
	}

	public void setBaixas(List<Lancamento> baixas) {
		this.baixas = baixas;
	}

}

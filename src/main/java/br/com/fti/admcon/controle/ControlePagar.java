package br.com.fti.admcon.controle;

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

import br.com.fti.admcon.entidade.empresa.Banco;
import br.com.fti.admcon.entidade.empresa.BancoPeriodo;
import br.com.fti.admcon.entidade.empresa.Conta;
import br.com.fti.admcon.entidade.empresa.Fornecedor;
import br.com.fti.admcon.entidade.empresa.Lancamento;
import br.com.fti.admcon.entidade.empresa.Pagar;
import br.com.fti.admcon.enums.DebitoCredito;
import br.com.fti.admcon.enums.OrigemLcto;
import br.com.fti.admcon.servico.SerBanco;
import br.com.fti.admcon.servico.SerPagar;
import br.com.fti.admcon.util.FacesMessages;


/****************************************************************************
 * Classe controle para View da Tela do Lançamento a Pagar
 * 
 * @author: Thayro Rodrigues - 24/04/2017
 ****************************************************************************/

@Named
@ViewScoped
public class ControlePagar implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;
	
	private List<Pagar> listaPagar = new ArrayList<Pagar>();
	private List<Lancamento> baixas = new ArrayList<Lancamento>();
	private Pagar pagar = new Pagar();
	private Pagar pagarSelect;
	private Fornecedor fornecedor;
	private Conta conta;
	private Lancamento lancamento = new Lancamento();
	private Banco banco;
	private boolean displayCheque = false;
	
	private boolean salvarTitulo = true;
	
	@Autowired
	SerPagar serPagar;
	@Autowired
	SerBanco serBanco;
	@Autowired
	FacesMessages mensagens;
	
	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {
		RequestContext.getCurrentInstance().execute("PF('wgSelecaoFornecedor').show();");
	}
	
	/****************************************************************************
	 * Salvar o lançamento a receber
	 ****************************************************************************/
	public void salvar() {
		try {
			pagar.setConta(conta);
			serPagar.salvar(pagar);
			confirmaFornecedor();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			pagarSelect = null;
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
		pagar = new Pagar();
		pagar.setFornecedor(fornecedor);
		pagar.setLancamento(new Date());
		pagar.setValor(new BigDecimal(0));
		pagarSelect = null;
		baixas = new ArrayList<Lancamento>();
	}
	
	/****************************************************************************
	 * Novo o lançamento a receber
	 ****************************************************************************/
	public void editLancamento() {
		salvarTitulo = true;
		pagar = pagarSelect;
		conta = pagarSelect.getConta();
		baixas = pagarSelect.getBaixas();
		if (pagar.getBaixas() != null && !pagar.getBaixas().isEmpty()) {
			salvarTitulo = false;
			mensagens.warning("Título já está com baixa!");
		}		
	}
	
	/****************************************************************************
	 * Excluir registro selecionado
	 ****************************************************************************/
	
	public void excluir() {
		try {
			serPagar.excluir(pagarSelect);
			confirmaFornecedor();
			mensagens.info("Registro excluido com sucesso!");
			
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		pagarSelect = null;
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}
	
	/****************************************************************************
	 * Resgata o Fornecedor selecionado no dialogo
	 ****************************************************************************/
	public void fornecedorSelecionado(SelectEvent event) {
		fornecedor = (Fornecedor) event.getObject();
	}
	
	/****************************************************************************
	 * Efetua a confirmação do fornecedor selecionado, faz a busca das duplicatas
	 ****************************************************************************/
	public void confirmaFornecedor() {
		if (fornecedor != null) {
			listaPagar = serPagar.listarPorFornecedor(fornecedor);
			RequestContext.getCurrentInstance().execute("PF('wgSelecaoFornecedor').hide();");
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
		lancamento.setPagar(pagarSelect);
		banco = new Banco();		
		
		String historico = "PAGAMENTO " +
							pagarSelect.getFornecedor().getRazaoSocial() +
							" DUPLICATA " +
							pagarSelect.getDocumento();
		
		lancamento.setDataLcto(new Date());
		lancamento.setValorBase(pagarSelect.getSaldo());
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
				lancamento.setConta(pagarSelect.getFornecedor().getConta());
				lancamento.setOrigemLcto(OrigemLcto.DCP);
				lancamento.setTipoLcto(DebitoCredito.DEBITO);
								
				pagarSelect.addBaixa(lancamento);
				serPagar.salvar(pagarSelect);
				
				confirmaFornecedor();
				pagarSelect = null;
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
			serPagar.estornarBaixa(pagarSelect, baixa.getIdLcto());	
			pagarSelect = null;

			confirmaFornecedor();
			mensagens.info("Estorno efetuado com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			RequestContext.getCurrentInstance().execute("PF('wgDadosLancamento').hide();");
					
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}		
	}
	
	/****************************************************************************
	 * Evento quando é feita alteração no tipo de lançamento
	 ****************************************************************************/
	public void changeTipoLcto() {
		lancamento.setCheque(false);
		displayCheque = false;
		if (lancamento.getTipoLcto() == DebitoCredito.DEBITO) {
			displayCheque = true;
		}
	}
	
	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public List<Pagar> getListaPagar() {
		return listaPagar;
	}

	public void setListaPagar(List<Pagar> listaPagar) {
		this.listaPagar = listaPagar;
	}

	public List<Lancamento> getBaixas() {
		return baixas;
	}

	public void setBaixas(List<Lancamento> baixas) {
		this.baixas = baixas;
	}

	public Pagar getPagar() {
		return pagar;
	}

	public void setPagar(Pagar pagar) {
		this.pagar = pagar;
	}

	public Pagar getPagarSelect() {
		return pagarSelect;
	}

	public void setPagarSelect(Pagar pagarSelect) {
		this.pagarSelect = pagarSelect;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
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

	public void setSalvarTitulo(boolean salvarTitulo) {
		this.salvarTitulo = salvarTitulo;
	}

	public SerPagar getSerPagar() {
		return serPagar;
	}

	public void setSerPagar(SerPagar serPagar) {
		this.serPagar = serPagar;
	}

	public SerBanco getSerBanco() {
		return serBanco;
	}

	public void setSerBanco(SerBanco serBanco) {
		this.serBanco = serBanco;
	}

	public FacesMessages getMensagens() {
		return mensagens;
	}

	public void setMensagens(FacesMessages mensagens) {
		this.mensagens = mensagens;
	}
	
	public boolean isDisplayCheque() {
		return displayCheque;
	}
	
	
}

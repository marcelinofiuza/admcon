package br.com.fti.admcon.controleTela.modulos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import br.com.fti.admcon.modulos.entidades.empresa.Conta;
import br.com.fti.admcon.modulos.entidades.empresa.Lancamento;
import br.com.fti.admcon.modulos.servicos.SerBanco;
import br.com.fti.admcon.modulos.servicos.SerLancamento;
import br.com.fti.admcon.util.ferramentas.FacesMessages;

/****************************************************************************
 * Classe controle para View da Tela do Lançamento Bancário
 * 
 * @author: Bob-Odin - 22/03/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControleLancamento implements Serializable {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	private static final long serialVersionUID = 1L;

	private Banco banco;
	private Banco bancoCredito;
	private BancoPeriodo bancoPeriodo;
	private List<BancoPeriodo> listaPeriodo = new ArrayList<>();
	private Lancamento lancamento = new Lancamento();
	private Lancamento lctoSelect;
	private Conta conta;
	private boolean displayCheque = false;

	@Autowired
	SerBanco serBanco;
	@Autowired
	SerLancamento serLancamento;
	@Autowired
	FacesMessages mensagens;

	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/
	@PostConstruct
	public void init() {
		RequestContext.getCurrentInstance().execute("PF('wgSelecaoBanco').show();");
	}

	/****************************************************************************
	 * Salvar o lançamento contábil
	 ****************************************************************************/
	public void salvar() {
		try {
			lancamento.setConta(conta);
			serLancamento.salvar(lancamento);
			resgataPeriodo();
			mensagens.info("Registro salvo com sucesso!");
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error(e.getMessage());
		}
	}

	/****************************************************************************
	 * Atribuir no controle o registro selecionado na tela
	 ****************************************************************************/
	public void editLcto() {
		lancamento = lctoSelect;
		conta = lancamento.getConta();
	}

	/****************************************************************************
	 * Excluir registro selecionado
	 ****************************************************************************/
	public void excluir() {
		try {
			if(lctoSelect.getOrigemLcto() == OrigemLcto.TRF ||
			   lctoSelect.getOrigemLcto() == OrigemLcto.BCO){
			
				serLancamento.excluir(lctoSelect);
				resgataPeriodo();
				mensagens.info("Registro excluido com sucesso!");
				
			} else {
				mensagens.error("Exclusão deve ser feito na origem do lançamento!");
			}
		} catch (Exception e) {
			mensagens.error(e.getMessage());
		}
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:tabela"));
	}

	/****************************************************************************
	 * Resgata o periodo selecionado
	 ****************************************************************************/
	public void resgataPeriodo() {
		
		//atualiza dados do banco selecionado
		Long idPeriodo = bancoPeriodo.getIdPeriodo();
		banco = serBanco.buscarPorId(banco.getIdBanco());
		serBanco.montaSaldo(banco, idPeriodo);

		//Resgatar periodo atualizado
		bancoPeriodo = banco.getPeriodo(idPeriodo);
				
		lctoSelect = null;
	}

	/****************************************************************************
	 * Resgata o Banco selecionado no dialogo
	 ****************************************************************************/
	public void bancoSelecionado(SelectEvent event) {
		banco = (Banco) event.getObject();
		listaPeriodo = banco.getPeriodos();
	}

	/****************************************************************************
	 * Efetua a confirmação do banco e periodo selecionado
	 ****************************************************************************/
	public void confirmaBanco() {		
		if(bancoPeriodo != null){
			resgataPeriodo();
			RequestContext.getCurrentInstance().execute("PF('wgSelecaoBanco').hide();");
		}else{
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error("Nenhum periodo para lançamento!");			
		}
	}

	/****************************************************************************
	 * Resgata a conta selecionada no dialogo
	 ****************************************************************************/
	public void contaSelecionada(SelectEvent event) {
		conta = (Conta) event.getObject();
	}

	/****************************************************************************
	 * Preparar objetos para novo lancamento
	 ****************************************************************************/
	public void novoLancamento() {
		conta = new Conta();
		lancamento = new Lancamento();
		lancamento.setOrigemLcto(OrigemLcto.BCO);
		lancamento.setBancoPeriodo(bancoPeriodo);
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
	 * Resgata o banco selecionada no dialogo
	 ****************************************************************************/
	public void bancoTransfSelecionada(SelectEvent event) {
		bancoCredito = (Banco) event.getObject();
	}

	/****************************************************************************
	 * Prepara objetos para nova transferencia
	 ****************************************************************************/
	public void novaTransferencia() {
		bancoCredito = new Banco();
		lancamento = new Lancamento();
		lancamento.setBancoPeriodo(bancoPeriodo);
		lancamento.setOrigemLcto(OrigemLcto.TRF);
		lancamento.setTipoLcto(DebitoCredito.DEBITO);
	}

	/****************************************************************************
	 * Salva o registro de transferencia
	 ****************************************************************************/
	public void salvaTransferencia() {

		BancoPeriodo perTransf = serBanco.selecionaPeriodo(bancoCredito, lancamento.getDataLcto());

		// se não encontrou periodo para o banco credor, exibe erro
		if (perTransf != null) {

			Lancamento transferencia = new Lancamento();
			transferencia.setBancoPeriodo(perTransf);
			transferencia.setOrigemLcto(lancamento.getOrigemLcto());
			transferencia.setDataLcto(lancamento.getDataLcto());
			transferencia.setTipoLcto(DebitoCredito.CREDITO);
			transferencia.setDocumento(lancamento.getDocumento());
			transferencia.setCheque(false);
			transferencia.setValorLcto(lancamento.getValorLcto());
			transferencia.setConta(banco.getConta());// conta banco-contrapartida
			transferencia.setHistorico(lancamento.getHistorico());

			lancamento.setConta(bancoCredito.getConta());
			lancamento.setTransferencia(transferencia);

			try {
				serLancamento.salvar(lancamento);
				resgataPeriodo();
				mensagens.info("Transferencia salva com sucesso!");
				RequestContext.getCurrentInstance().update(Arrays.asList("frm:msg-frm", "frm:toolbar", "frm:tabela"));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().validationFailed();
				mensagens.error(e.getMessage());
			}

		} else {
			FacesContext.getCurrentInstance().validationFailed();
			mensagens.error("Não existe periodo de lançamento para o banco crédito");
		}

	}

	/****************************************************************************
	 * Gets e Sets do controle
	 ****************************************************************************/

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public BancoPeriodo getBancoPeriodo() {
		return bancoPeriodo;
	}

	public void setBancoPeriodo(BancoPeriodo bancoPeriodo) {
		this.bancoPeriodo = bancoPeriodo;
	}

	public List<BancoPeriodo> getListaPeriodo() {
		return listaPeriodo;
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public Lancamento getLctoSelect() {
		return lctoSelect;
	}

	public void setLctoSelect(Lancamento lctoSelect) {
		this.lctoSelect = lctoSelect;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public boolean isDisplayCheque() {
		return displayCheque;
	}

	public Banco getBancoCredito() {
		return bancoCredito;
	}

	public void setBancoCredito(Banco bancoCredito) {
		this.bancoCredito = bancoCredito;
	}

}

package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.enums.DebitoCredito;
import br.com.fti.admcon.enums.OrigemLcto;
import br.com.fti.admcon.tenancy.ZEmpresa;


/****************************************************************************
 * Entidade Lancamento, para gravação dos lançamentos contábeis
 * 
 * @author Bob-Odin - 22/03/2017
 ****************************************************************************/
@Entity
public class Lancamento extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 6570030840432695306L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLcto;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idPeriodo")
	private BancoPeriodo bancoPeriodo;

	@Enumerated(EnumType.STRING)
	@Column(length = 3)
	private OrigemLcto origemLcto;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataLcto;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private DebitoCredito tipoLcto;

	@NotEmpty
	@Column(length = 15)
	private String documento;

	private Boolean cheque;

	@NotNull(message = "Informar o valor do Lançamento!")
	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")	
	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorBase;

	@NotNull(message = "Informar o valor do Lançamento!")
	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal juros;

	@NotNull(message = "Informar o valor do Lançamento!")
	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal multa;

	@NotNull(message = "Informar o valor do Lançamento!")
	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal desconto;

	@NotNull(message = "Informar o valor do Lançamento!")
	@DecimalMin(value = "0.01", message = "Não pode ser menor que 0,01")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorLcto;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal saldo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@NotEmpty
	@Column(length = 250)
	private String historico;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idLctoTransf")
	private Lancamento transferencia;

	@ManyToOne
	@JoinColumn(name = "idReceber")
	private Receber receber;

	@ManyToOne
	@JoinColumn(name = "idPagar")
	private Pagar pagar;
	
	public Lancamento() {
		this.valorBase = new BigDecimal(0);
		this.juros = new BigDecimal(0);
		this.multa = new BigDecimal(0);
		this.desconto = new BigDecimal(0);
		this.valorLcto = new BigDecimal(0);
	}

	public Long getIdLcto() {
		return idLcto;
	}

	public void setIdLcto(Long idLcto) {
		this.idLcto = idLcto;
	}

	public BancoPeriodo getBancoPeriodo() {
		return bancoPeriodo;
	}

	public void setBancoPeriodo(BancoPeriodo bancoPeriodo) {
		this.bancoPeriodo = bancoPeriodo;
	}

	public OrigemLcto getOrigemLcto() {
		return origemLcto;
	}

	public void setOrigemLcto(OrigemLcto origemLcto) {
		this.origemLcto = origemLcto;
	}

	public Date getDataLcto() {
		return dataLcto;
	}

	public void setDataLcto(Date dataLcto) {
		this.dataLcto = dataLcto;
	}

	public DebitoCredito getTipoLcto() {
		return tipoLcto;
	}

	public void setTipoLcto(DebitoCredito tipoLcto) {
		this.tipoLcto = tipoLcto;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Boolean getCheque() {
		return cheque;
	}

	public void setCheque(Boolean cheque) {
		this.cheque = cheque;
	}

	public BigDecimal getValorBase() {
		valorBase = valorLcto;
		valorBase = valorBase.add(desconto);
		valorBase = valorBase.subtract(juros);
		valorBase = valorBase.subtract(multa);
		return valorBase;
	}

	public void setValorBase(BigDecimal valorBase){
		this.valorLcto = valorBase;
	}
	
	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getValorLcto() {
		return valorLcto;
	}

	public BigDecimal getValorLctoConvertido() {
		return valorLcto.multiply(BigDecimal.valueOf(tipoLcto.getMultiplicador()));
	}

	public void setValorLcto(BigDecimal valorLcto) {
		this.valorLcto = valorLcto;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Lancamento getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Lancamento transferencia) {
		this.transferencia = transferencia;
	}
		
	public Receber getReceber() {
		return receber;
	}

	public void setReceber(Receber receber) {
		this.receber = receber;
	}
	
	public Pagar getPagar() {
		return pagar;
	}

	public void setPagar(Pagar pagar) {
		this.pagar = pagar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLcto == null) ? 0 : idLcto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Lancamento))
			return false;
		Lancamento other = (Lancamento) obj;
		if (idLcto == null) {
			if (other.idLcto != null)
				return false;
		} else if (!idLcto.equals(other.idLcto))
			return false;
		return true;
	}

}

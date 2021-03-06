package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.fti.admcon.enums.StatusBoleto;
import br.com.fti.admcon.tenancy.ZEmpresa;


/****************************************************************************
 * Entidade Boleto Desenvolvido por :
 * 
 * @author Bob-Odin - 07/04/2017
 ****************************************************************************/
@Entity
public class Boleto extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 3452852491737648998L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBoleto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idBanco")
	private Banco banco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idConta")
	private Conta conta;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date lancamento;

	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private StatusBoleto statusBoleto;

	@Column(length = 80)
	private String insLinha01;

	@Column(length = 80)
	private String insLinha02;

	@Column(length = 80)
	private String insLinha03;

	@Column(length = 80)
	private String insLinha04;

	@Column(length = 80)
	private String insLinha05;

	@Column(length = 80)
	private String insLinha06;

	@Column(length = 80)
	private String insLinha07;

	@Column(length = 80)
	private String insLinha08;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idBoleto")
	private List<BoletoItem> itens = new ArrayList<BoletoItem>();

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getLancamento() {
		return lancamento;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public StatusBoleto getStatusBoleto() {
		return statusBoleto;
	}

	public void setStatusBoleto(StatusBoleto statusBoleto) {
		this.statusBoleto = statusBoleto;
	}

	public String getInsLinha01() {
		return insLinha01;
	}

	public void setInsLinha01(String insLinha01) {
		this.insLinha01 = insLinha01;
	}

	public String getInsLinha02() {
		return insLinha02;
	}

	public void setInsLinha02(String insLinha02) {
		this.insLinha02 = insLinha02;
	}

	public String getInsLinha03() {
		return insLinha03;
	}

	public void setInsLinha03(String insLinha03) {
		this.insLinha03 = insLinha03;
	}

	public String getInsLinha04() {
		return insLinha04;
	}

	public void setInsLinha04(String insLinha04) {
		this.insLinha04 = insLinha04;
	}

	public String getInsLinha05() {
		return insLinha05;
	}

	public void setInsLinha05(String insLinha05) {
		this.insLinha05 = insLinha05;
	}

	public String getInsLinha06() {
		return insLinha06;
	}

	public void setInsLinha06(String insLinha06) {
		this.insLinha06 = insLinha06;
	}

	public String getInsLinha07() {
		return insLinha07;
	}

	public void setInsLinha07(String insLinha07) {
		this.insLinha07 = insLinha07;
	}

	public String getInsLinha08() {
		return insLinha08;
	}

	public void setInsLinha08(String insLinha08) {
		this.insLinha08 = insLinha08;
	}

	public List<BoletoItem> getItens() {
		return itens;
	}

	public void setItens(List<BoletoItem> itens) {
		this.itens = itens;
	}

	public void addItem(BoletoItem item) {
		item.setBoleto(this);
		itens.add(item);
	}

	public BigDecimal getValorBoletos() {
		BigDecimal totalBoleto = new BigDecimal(0);
		for (BoletoItem boletoItem : itens) {
			totalBoleto = totalBoleto.add(boletoItem.getValor0());
			totalBoleto = totalBoleto.add(boletoItem.getValor1());
			totalBoleto = totalBoleto.add(boletoItem.getValor2());
			totalBoleto = totalBoleto.add(boletoItem.getValor3());
		}
		return totalBoleto;
	}
	
	public Integer getNumBoletos(){
		return itens.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBoleto == null) ? 0 : idBoleto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Boleto))
			return false;
		Boleto other = (Boleto) obj;
		if (idBoleto == null) {
			if (other.idBoleto != null)
				return false;
		} else if (!idBoleto.equals(other.idBoleto))
			return false;
		return true;
	}

}

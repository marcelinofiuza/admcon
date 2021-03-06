package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade BoletoItem Desenvolvido por :
 * 
 * @author Bob-Odin - 07/04/2017
 ****************************************************************************/
@Entity
public class BoletoItem extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 6234468125285060375L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idItem;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBoleto")
	private Boleto boleto;

	private Long documento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date vencimento;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor0;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor1;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor2;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor3;

	public BoletoItem(){
		valor0 = new BigDecimal(0);
		valor1 = new BigDecimal(0);
		valor2 = new BigDecimal(0);
		valor3 = new BigDecimal(0);
	}	
	
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public Long getDocumento() {
		return documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public BigDecimal getValor0() {
		return valor0;
	}

	public void setValor0(BigDecimal valor0) {
		this.valor0 = valor0;
	}

	public BigDecimal getValor1() {
		return valor1;
	}

	public void setValor1(BigDecimal valor1) {
		this.valor1 = valor1;
	}

	public BigDecimal getValor2() {
		return valor2;
	}

	public void setValor2(BigDecimal valor2) {
		this.valor2 = valor2;
	}

	public BigDecimal getValor3() {
		return valor3;
	}

	public void setValor3(BigDecimal valor3) {
		this.valor3 = valor3;
	}

	public BigDecimal getTotalItem(){		
		BigDecimal totalItem = new BigDecimal(0);		
		totalItem = totalItem.add(valor0);
		totalItem = totalItem.add(valor1);
		totalItem = totalItem.add(valor2);
		totalItem = totalItem.add(valor3);		
		return totalItem;		
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BoletoItem))
			return false;
		BoletoItem other = (BoletoItem) obj;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		return true;
	}

}

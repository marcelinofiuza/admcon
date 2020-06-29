package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Itens Orcamento de vendas
 * 
 * @author Bob-Odin - 15/03/2020
 ****************************************************************************/
@Entity
public class OcmItem extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 3199605331497282892L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idItem;

	// Faz referencia com join do ocmHeader
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idOrcamento")
	private OcmHeader ocmHeader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduto")
	private Produto produto;

	@Column(length = 250)
	private String observacao;

	@NumberFormat(pattern = "#,##0.000")
	private BigDecimal quantidade;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal unitario;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal total;

	
	public OcmItem() {
		quantidade = new BigDecimal(0);
		unitario = new BigDecimal(0);
		total = new BigDecimal(0);
	}
	
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public OcmHeader getOcmHeader() {
		return ocmHeader;
	}

	public void setOcmHeader(OcmHeader ocmHeader) {
		this.ocmHeader = ocmHeader;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getUnitario() {
		return unitario;
	}

	public void setUnitario(BigDecimal unitario) {
		this.unitario = unitario;
	}

	public BigDecimal getTotal() {
		this.total = this.unitario.multiply(this.quantidade);
		return this.total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idItem == null) ? 0 : idItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OcmItem other = (OcmItem) obj;
		if (idItem == null) {
			if (other.idItem != null)
				return false;
		} else if (!idItem.equals(other.idItem))
			return false;
		return true;
	}

}

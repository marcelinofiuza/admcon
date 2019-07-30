package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.enums.OrigemEstoque;
import br.com.fti.admcon.enums.SentidoEstoque;
import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Estoque Desenvolvido por :
 * 
 * @author Bob-Odin - 15/07/2018
 ****************************************************************************/
@Entity
public class EstoqueLancamento extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = -4567828841246475581L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLancamento;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idEstoque")
	private EstoqueHeader estoqueHeader;

	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	private SentidoEstoque sentidoEstoque;

	@Enumerated(EnumType.STRING)
	@Column(length = 6)
	private OrigemEstoque origemEstoque;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataLcto;

	@NotEmpty
	@Column(length = 15)
	private String documento;

	@NotNull(message = "Informar a quantidade!")
	@DecimalMin(value = "0.000", message = "Não pode ser menor que 0,000")
	@DecimalMax(value = "99999999.999", message = "Máximo deve ser 99.999.999,999")
	@NumberFormat(pattern = "#,##0.000")
	private Double quantidade;

	@NotNull(message = "Informar o valor unitário!")
	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.999", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private Double unitario;

	public Long getIdLancamento() {
		return idLancamento;
	}

	public void setIdLancamento(Long idLancamento) {
		this.idLancamento = idLancamento;
	}

	public EstoqueHeader getEstoqueHeader() {
		return estoqueHeader;
	}

	public void setEstoqueHeader(EstoqueHeader estoqueHeader) {
		this.estoqueHeader = estoqueHeader;
	}

	public SentidoEstoque getSentidoEstoque() {
		return sentidoEstoque;
	}

	public void setSentidoEstoque(SentidoEstoque sentidoEstoque) {
		this.sentidoEstoque = sentidoEstoque;
	}

	public OrigemEstoque getOrigemEstoque() {
		return origemEstoque;
	}

	public void setOrigemEstoque(OrigemEstoque origemEstoque) {
		this.origemEstoque = origemEstoque;
	}

	public Date getDataLcto() {
		return dataLcto;
	}

	public void setDataLcto(Date dataLcto) {
		this.dataLcto = dataLcto;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getUnitario() {
		return unitario;
	}

	public void setUnitario(Double unitario) {
		this.unitario = unitario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idLancamento == null) ? 0 : idLancamento.hashCode());
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
		EstoqueLancamento other = (EstoqueLancamento) obj;
		if (idLancamento == null) {
			if (other.idLancamento != null)
				return false;
		} else if (!idLancamento.equals(other.idLancamento))
			return false;
		return true;
	}

}

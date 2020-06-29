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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.enums.StatusVenda;
import br.com.fti.admcon.tenancy.ZEmpresa;
import br.com.fti.admcon.util.ferramentas.R42Data;

/****************************************************************************
 * Entidade Cabeçalho Orcamento de vendas
 * 
 * @author Bob-Odin - 15/03/2020
 ****************************************************************************/
@Entity
public class OcmHeader extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = -5833582562299978767L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idOrcamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;

	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	private StatusVenda status;

	@Column(length = 250)
	private String observacao;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal frete;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal totalItens;

	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal totalOrcamento;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idOrcamento")
	private List<OcmItem> ocmItem = new ArrayList<OcmItem>();

	public OcmHeader() {

		this.data = R42Data.dataAtual();
		this.status = StatusVenda.A;
		this.frete = new BigDecimal(0);
		this.totalItens = new BigDecimal(0);
		this.totalOrcamento = new BigDecimal(0);
	}

	public Long getIdOrcamento() {
		return idOrcamento;
	}

	public void setIdOrcamento(Long idOrcamento) {
		this.idOrcamento = idOrcamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public StatusVenda getStatus() {
		return status;
	}

	public void setStatus(StatusVenda status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getFrete() {
		return frete;
	}

	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}

	public BigDecimal getTotalItens() {

		this.totalItens = new BigDecimal(0);

		for (OcmItem wOcmItem : ocmItem) {
			this.totalItens.add(wOcmItem.getTotal());
		}

		return this.totalItens;
	}

	public BigDecimal getTotalOrcamento() {

		this.totalOrcamento = new BigDecimal(0);

		this.totalOrcamento.add(getTotalItens());

		if (this.getFrete() != null) {
			this.totalOrcamento.add(getFrete());
		}
		return this.totalOrcamento;

	}

	public List<OcmItem> getOcmItem() {
		return ocmItem;
	}

	public void setOcmItem(List<OcmItem> ocmItem) {
		this.ocmItem = ocmItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idOrcamento == null) ? 0 : idOrcamento.hashCode());
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
		OcmHeader other = (OcmHeader) obj;
		if (idOrcamento == null) {
			if (other.idOrcamento != null)
				return false;
		} else if (!idOrcamento.equals(other.idOrcamento))
			return false;
		return true;
	}

}

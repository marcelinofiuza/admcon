package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Produto componente Desenvolvido por :
 * 
 * @author Bob-Odin - 10/07/2019
 ****************************************************************************/
@Entity
public class ProdutoComponente extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 7618694755835943275L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idComponente;

	// Faz referencia com join do produto
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idProduto")
	private Produto produto;

	// Produto da lista de componentes
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idItem")
	private Produto itemProduto = new Produto();

	@NotNull(message = "Quantidade não pode ser menor que zero")
	@DecimalMin(value = "0.001", message = "Não pode ser menor que 0,001")
	@DecimalMax(value = "99999999.999", message = "Máximo deve ser 99.999.999,999")
	@NumberFormat(pattern = "#,##0.000")
	private BigDecimal qtdUtilizada;

	public Long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getItemProduto() {
		return itemProduto;
	}

	public void setItemProduto(Produto itemProduto) {
		this.itemProduto = itemProduto;
	}

	public BigDecimal getQtdUtilizada() {
		return qtdUtilizada;
	}

	public void setQtdUtilizada(BigDecimal qtdUtilizada) {
		this.qtdUtilizada = qtdUtilizada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idComponente == null) ? 0 : idComponente.hashCode());
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
		ProdutoComponente other = (ProdutoComponente) obj;
		if (idComponente == null) {
			if (other.idComponente != null)
				return false;
		} else if (!idComponente.equals(other.idComponente))
			return false;
		return true;
	}

}

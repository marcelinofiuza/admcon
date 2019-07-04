package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 24/06/2018
 ****************************************************************************/

public class Produto extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idProduto;

	@Column(length = 100)
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoria")
	private ProdutoCategoria categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUM")
	private ProdutoUM produtoUM;

	private boolean controlaEstoque;

	private boolean ativo;

	
	private BigDecimal saldoEstoque;

	private BigDecimal valorCusto;

	@DecimalMin(value = "100,000", message = "Não pode ser menor que 1,000")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")	
	private BigDecimal aliqAcrescimo;

	private BigDecimal valorAcrescimo;

	private BigDecimal precoVenda;

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProdutoCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(ProdutoCategoria categoria) {
		this.categoria = categoria;
	}

	public ProdutoUM getProdutoUM() {
		return produtoUM;
	}

	public void setProdutoUM(ProdutoUM produtoUM) {
		this.produtoUM = produtoUM;
	}

	public boolean isControlaEstoque() {
		return controlaEstoque;
	}

	public void setControlaEstoque(boolean controlaEstoque) {
		this.controlaEstoque = controlaEstoque;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getSaldoEstoque() {
		return saldoEstoque;
	}

	public void setSaldoEstoque(BigDecimal saldoEstoque) {
		this.saldoEstoque = saldoEstoque;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public BigDecimal getAliqAcrescimo() {
		return aliqAcrescimo;
	}

	public void setAliqAcrescimo(BigDecimal aliqAcrescimo) {
		this.aliqAcrescimo = aliqAcrescimo;
	}

	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProduto == null) ? 0 : idProduto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		return true;
	}

}

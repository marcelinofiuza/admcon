package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Produto Desenvolvido por :
 * 
 * @author Bob-Odin - 24/06/2018
 ****************************************************************************/
@Entity
public class Produto extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 708834415820005620L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idProduto;

	@Column(length = 100)
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGrupo")
	private ProdutoGrupo grupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoria")
	private ProdutoCategoria categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMedida")
	private ProdutoMedida medida;

	private boolean ativo;

	@Column(length = 50)
	private String sku;
	
	@Column(length = 50)
	private String gtin;
	
	@Column(length = 50)
	private String mpn;
	
	@Column(length = 50)
	private String ncm;

	@DecimalMin(value = "0.000", message = "Não pode ser menor que 0,000")
	@DecimalMax(value = "99999.999", message = "Máximo deve ser 99.999,999")
	@NumberFormat(pattern = "#,##0.000")
	private BigDecimal peso;

	@DecimalMin(value = "0", message = "Não pode ser menor que 0")
	@DecimalMax(value = "999999999", message = "Máximo deve ser 999.999.999")	
	@NumberFormat(pattern = "#,##0")
	private BigDecimal altura;
	
	@DecimalMin(value = "0", message = "Não pode ser menor que 0")
	@DecimalMax(value = "999999999", message = "Máximo deve ser 999.999.999")	
	@NumberFormat(pattern = "#,##0")
	private BigDecimal largura;
	
	@DecimalMin(value = "0", message = "Não pode ser menor que 0")
	@DecimalMax(value = "999999999", message = "Máximo deve ser 999.999.999")	
	@NumberFormat(pattern = "#,##0")
	private BigDecimal profundidade;
	
	@Transient
	@DecimalMin(value = "0.000", message = "Não pode ser menor que 0,000")
	@DecimalMax(value = "9999.999", message = "Máximo deve ser 9.999,999")
	@NumberFormat(pattern = "#,##0.000")
	private BigDecimal saldoEstoque;

	@Transient
	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "9999.99", message = "Máximo deve ser 9.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorCusto;

	@DecimalMin(value = "0.000", message = "Não pode ser menor que 0,000")
	@DecimalMax(value = "9999.999", message = "Máximo deve ser 9.999,999")
	@NumberFormat(pattern = "#,##0.000")
	private BigDecimal aliqAcrescimo;

	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorAcrescimo;

	@DecimalMin(value = "0.00", message = "Não pode ser menor que 0,00")
	@DecimalMax(value = "99999999.99", message = "Máximo deve ser 99.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal precoVenda;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduto")
	private List<ProdutoComponente> componentes = new ArrayList<ProdutoComponente>();

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

	public ProdutoGrupo getGrupo() {
		return grupo;
	}

	public void setGrupo(ProdutoGrupo grupo) {
		this.grupo = grupo;
	}

	public ProdutoCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(ProdutoCategoria categoria) {
		this.categoria = categoria;
	}

	public ProdutoMedida getMedida() {
		return medida;
	}

	public void setMedida(ProdutoMedida medida) {
		this.medida = medida;
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

	public List<ProdutoComponente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<ProdutoComponente> componentes) {
		this.componentes = componentes;
	}
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getMpn() {
		return mpn;
	}

	public void setMpn(String mpn) {
		this.mpn = mpn;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getLargura() {
		return largura;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public BigDecimal getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(BigDecimal profundidade) {
		this.profundidade = profundidade;
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

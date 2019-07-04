package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Cobrança Item Desenvolvido por :
 * 
 * @author Bob-Odin - 27/06/2019
 ****************************************************************************/

public class ProdutoUM extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 5)
	private String idUM;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	@DecimalMin(value = "0.0000", message = "Não pode ser menor que 0,0000")
	@DecimalMax(value = "99999999.9999", message = "Máximo deve ser 9999,9999")	
	@NumberFormat(pattern = "#,##0.0000")
	private BigDecimal numeral;

	public String getIdUM() {
		return idUM;
	}

	public void setIdUM(String idUM) {
		this.idUM = idUM;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getNumeral() {
		return numeral;
	}

	public void setNumeral(BigDecimal numeral) {
		this.numeral = numeral;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idUM == null) ? 0 : idUM.hashCode());
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
		ProdutoUM other = (ProdutoUM) obj;
		if (idUM == null) {
			if (other.idUM != null)
				return false;
		} else if (!idUM.equals(other.idUM))
			return false;
		return true;
	}

}

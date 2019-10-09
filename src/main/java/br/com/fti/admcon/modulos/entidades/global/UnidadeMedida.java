package br.com.fti.admcon.modulos.entidades.global;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

/****************************************************************************
 * Entidade Produto UM Desenvolvido por :
 * 
 * @author Bob-Odin - 27/06/2019
 ****************************************************************************/
@Entity
public class UnidadeMedida implements Serializable {

	private static final long serialVersionUID = -352975428642532199L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUm;

	@NotEmpty
	@Column(length = 5)
	private String unidade;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	@DecimalMin(value = "0.0000", message = "Não pode ser menor que 0,0000")
	@DecimalMax(value = "99999999.9999", message = "Máximo deve ser 9999,9999")
	@NumberFormat(pattern = "#,##0.0000")
	private BigDecimal numeral;

	public Long getIdUm() {
		return idUm;
	}

	public void setIdUm(Long idUm) {
		this.idUm = idUm;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
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
		result = prime * result + ((idUm == null) ? 0 : idUm.hashCode());
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
		UnidadeMedida other = (UnidadeMedida) obj;
		if (idUm == null) {
			if (other.idUm != null)
				return false;
		} else if (!idUm.equals(other.idUm))
			return false;
		return true;
	}

}

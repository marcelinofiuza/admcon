package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Unidade de Medida Desenvolvido por :
 * 
 * @author Bob-Odin - 27/06/2019
 ****************************************************************************/
@Entity
public class ProdutoMedida extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 5268072184764578499L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idMedida;

	@NotEmpty
	@Column(length = 5)
	private String unidade;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal numeral;

	public Long getIdMedida() {
		return idMedida;
	}

	public void setIdMedida(Long idMedida) {
		this.idMedida = idMedida;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMedida == null) ? 0 : idMedida.hashCode());
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
		ProdutoMedida other = (ProdutoMedida) obj;
		if (idMedida == null) {
			if (other.idMedida != null)
				return false;
		} else if (!idMedida.equals(other.idMedida))
			return false;
		return true;
	}

	public BigDecimal getNumeral() {
		return numeral;
	}

	public void setNumeral(BigDecimal numeral) {
		this.numeral = numeral;
	}

}

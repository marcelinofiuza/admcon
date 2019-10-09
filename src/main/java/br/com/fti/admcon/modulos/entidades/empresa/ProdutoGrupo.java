package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Produto Categoria Desenvolvido por :
 * 
 * @author Bob-Odin - 09/10/2019
 ****************************************************************************/
@Entity
public class ProdutoGrupo extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = -9165587036420473647L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGrupo;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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
		int result = super.hashCode();
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		ProdutoGrupo other = (ProdutoGrupo) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}

}

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
 * @author Bob-Odin - 27/06/2019
 ****************************************************************************/
@Entity
public class ProdutoCategoria extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 5984668645399715338L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCategoria;

	@NotEmpty
	@Column(length = 50)
	private String descricao;

	private boolean componente;

	private boolean estoque;

	private boolean venda;

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isComponente() {
		return componente;
	}

	public void setComponente(boolean componente) {
		this.componente = componente;
	}

	public boolean isEstoque() {
		return estoque;
	}

	public void setEstoque(boolean estoque) {
		this.estoque = estoque;
	}

	public boolean isVenda() {
		return venda;
	}

	public void setVenda(boolean venda) {
		this.venda = venda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
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
		ProdutoCategoria other = (ProdutoCategoria) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		return true;
	}

}

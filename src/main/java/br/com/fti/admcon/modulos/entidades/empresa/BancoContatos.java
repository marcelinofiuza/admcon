package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.fti.admcon.modulos.entidades.embeddeble.Contato;
import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Contatos Banco Desenvolvido por :
 * 
 * @author Bob-Odin - 01/03/2017 /
 ****************************************************************************/
@Entity
public class BancoContatos extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = -6039648386007733756L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idContato;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBanco")
	private Banco banco;

	@Embedded
	private Contato contato = new Contato();
	
	public Long getIdContato() {
		return idContato;
	}

	public void setIdContato(Long idContato) {
		this.idContato = idContato;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idContato == null) ? 0 : idContato.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BancoContatos))
			return false;
		BancoContatos other = (BancoContatos) obj;
		if (idContato == null) {
			if (other.idContato != null)
				return false;
		} else if (!idContato.equals(other.idContato))
			return false;
		return true;
	}

}

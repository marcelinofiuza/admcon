package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.fti.admcon.enums.AnaliticaSintetica;
import br.com.fti.admcon.enums.AtivaItativa;
import br.com.fti.admcon.enums.Natureza;
import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Conta (Plano de Contas) Desenvolvido por :
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
@Entity
public class Conta extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = -7186722637034240606L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idConta;

	@NotNull
	@Column(nullable = false)
	private Integer chave;

	@Column(length = 10)
	private String reduzida;

	@NotEmpty
	@Column(length = 50, nullable = false)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private AnaliticaSintetica tipoConta;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private AtivaItativa status;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Natureza natureza;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idContaPai")
	private Conta contaPai;

	@OneToMany(mappedBy = "contaPai", fetch = FetchType.LAZY)
	private List<Conta> subConta = new ArrayList<Conta>();

	@Transient
	private String contafull;

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public Integer getChave() {
		return chave;
	}

	public void setChave(Integer chave) {
		this.chave = chave;
	}

	public String getReduzida() {
		return reduzida;
	}

	public void setReduzida(String reduzida) {
		this.reduzida = reduzida;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public AnaliticaSintetica getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(AnaliticaSintetica tipoConta) {
		this.tipoConta = tipoConta;
	}

	public AtivaItativa getStatus() {
		return status;
	}

	public void setStatus(AtivaItativa status) {
		this.status = status;
	}

	public Natureza getNatureza() {
		return natureza;
	}

	public void setNatureza(Natureza natureza) {
		this.natureza = natureza;
	}

	public Conta getContaPai() {
		return contaPai;
	}

	public void setContaPai(Conta contaPai) {
		this.contaPai = contaPai;
	}

	public List<Conta> getSubConta() {
		return subConta;
	}

	public void setSubConta(List<Conta> subConta) {
		this.subConta = subConta;
	}

	public String getIdeDescricao() {
		return idConta + " - " + descricao;
	}

	public String getContafull() {
		return contafull;
	}

	public void setContafull(String contafull) {
		this.contafull = contafull;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConta == null) ? 0 : idConta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Conta))
			return false;
		Conta other = (Conta) obj;
		if (idConta == null) {
			if (other.idConta != null)
				return false;
		} else if (!idConta.equals(other.idConta))
			return false;
		return true;
	}

}

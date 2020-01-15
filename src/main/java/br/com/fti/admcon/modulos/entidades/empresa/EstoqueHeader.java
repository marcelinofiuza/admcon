package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Cabeçalho do Estoque, para gravação de saldos e fechamento contabil
 * 
 * @author Bob-Odin - 16/07/2019
 ****************************************************************************/
@Entity
public class EstoqueHeader extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 4955577066145549691L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEstoque;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFinal;

	private boolean abertura;

	private boolean fechado;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstoque")
	private List<EstoqueItem> estoqueItem = new ArrayList<EstoqueItem>();

	public Long getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Long idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public boolean isAbertura() {
		return abertura;
	}

	public void setAbertura(boolean abertura) {
		this.abertura = abertura;
	}

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}

	public List<EstoqueItem> getEstoqueItem() {
		return estoqueItem;
	}

	public void setEstoqueItem(List<EstoqueItem> estoqueItem) {
		this.estoqueItem = estoqueItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idEstoque == null) ? 0 : idEstoque.hashCode());
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
		EstoqueHeader other = (EstoqueHeader) obj;
		if (idEstoque == null) {
			if (other.idEstoque != null)
				return false;
		} else if (!idEstoque.equals(other.idEstoque))
			return false;
		return true;
	}

}

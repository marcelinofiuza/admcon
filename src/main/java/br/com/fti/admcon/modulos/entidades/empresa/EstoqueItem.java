package br.com.fti.admcon.modulos.entidades.empresa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.format.annotation.NumberFormat;

import br.com.fti.admcon.tenancy.ZEmpresa;

/****************************************************************************
 * Entidade Itens do estoque, para gravação de saldos e fechamento contabil
 * 
 * @author Bob-Odin - 16/07/2019
 ****************************************************************************/
@Entity
public class EstoqueItem extends ZEmpresa implements Serializable {

	private static final long serialVersionUID = 718019860031007270L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idItem;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEstoque")
	private EstoqueHeader estoqueHeader;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduto")
	private Produto produto;
	
	@NumberFormat(pattern = "#,##0.000")
	private BigDecimal quantidade;
	
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal unitario;
	
	@Transient
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal total;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		EstoqueItem other = (EstoqueItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

}

package br.com.fti.admcon.tenancy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import br.com.fti.admcon.entidade.global.Empresa;
import br.com.fti.admcon.util.R42Util;

@MappedSuperclass
@FilterDef(name = "tenant", parameters = { @ParamDef(name = "id", type = "long") })
@Filter(name = "tenant", condition = "zempresa = :id")
public class ZEmpresa {

	@Column(name = "zempresa")
	private Long zempresa;

	public Long getZempresa() {
		return zempresa;
	}

	public void setZempresa(Long zempresa) {
		this.zempresa = zempresa;
	}

	@PrePersist
	@PreUpdate
	private void definirEmpresa() {
		Empresa empresa = R42Util.resgataEmpresa();
		if (empresa != null) {
			this.zempresa = empresa.getIdEmpresa();
		}
	}

}

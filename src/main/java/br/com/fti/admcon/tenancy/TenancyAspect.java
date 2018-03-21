package br.com.fti.admcon.tenancy;

import javax.persistence.EntityManager;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.fti.admcon.entidade.global.Empresa;
import br.com.fti.admcon.util.R42Util;

@Aspect
@Transactional(propagation = Propagation.REQUIRED)
@Component
public class TenancyAspect {

	@Autowired
	private EntityManager manager;
	
	@Before("execution(* br.com.fti.admcon.repositorio.empresa.*.*(..))")
	public void definirTenant() {
		Empresa empresa = R42Util.resgataEmpresa();		
		manager.unwrap(Session.class).enableFilter("tenant").setParameter("id", empresa.getIdEmpresa());
	}
	
}

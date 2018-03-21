package br.com.fti.admcon.controle;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.fti.admcon.entidade.global.Empresa;
import br.com.fti.admcon.entidade.global.Usuario;
import br.com.fti.admcon.servico.SerEmpresa;
import br.com.fti.admcon.servico.SerUsuario;

/****************************************************************************
 * Classe controle principal, para View da Telas Iniciais
 * 
 * @author: Bob-Odin - 06/02/2017
 ****************************************************************************/
@Named
@ViewScoped
public class ControlePrincipal {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/	
	public HttpSession httpSessao;
	public Usuario usuario;
	
	@Autowired
	SerUsuario serUsuario;	
	@Autowired
	SerEmpresa serEmpresa;	
	
	/****************************************************************************
	 * Inicialização
	 ****************************************************************************/	
	@PostConstruct
	public void inicio() {		
		session();
		resgataUsuario();
		exibePopUp();
	}
	
	/****************************************************************************
	 * Seta a empresa de trabalho
	 ****************************************************************************/
	public void selecionarEmpresa(Empresa empresa){		
		resgataUsuario();
		usuario.setEmpresaWork(empresa);
		httpSessao.setAttribute("USUARIO", usuario);		
	}
	
	/****************************************************************************
	 * pega sessão corrente
	 ****************************************************************************/		
	private void session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		httpSessao = attr.getRequest().getSession(true); // true == allow create
	}

	/****************************************************************************
	 * pega dados do usuário logado
	 ****************************************************************************/	
	private void resgataUsuario(){
				
		usuario = (Usuario) httpSessao.getAttribute("USUARIO");		
		
		if(usuario == null){			
			try {	
							
				SecurityContextImpl sci = (SecurityContextImpl) httpSessao.getAttribute("SPRING_SECURITY_CONTEXT");
				UserDetails user = (UserDetails) sci.getAuthentication().getPrincipal();
				usuario = serUsuario.buscarPorCredencial(user.getUsername());

				//Atualiza data de acesso do usuario
				if(usuario.getCredencial().equals("TAMAGU")) {					
					List<Empresa> empresas = serEmpresa.listarTodos();		
					if (empresas != null) {
						usuario.setEmpresas(empresas);
					}
				}				
				
				usuario.setUltimoAcesso(new Date(System.currentTimeMillis()));
				serUsuario.salvar(usuario);

				//limpa senha para deixar na memoria
				usuario.setSenha(null);
				httpSessao.setAttribute("USUARIO", usuario);	
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
				
	}

	/****************************************************************************
	 * exibe tela de pop-up para seleção de empresa na inicialização
	 ****************************************************************************/		
	private void exibePopUp(){
		if(!usuario.getEmpresas().isEmpty()){
			if(usuario.getEmpresaWork() == null){
				RequestContext.getCurrentInstance().execute("PF('wgDados').show();");
			}
		}
	}
	
	/****************************************************************************
	 * Gets e Sets
	 ****************************************************************************/
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public Empresa getEmpresa() {
		return usuario.getEmpresaWork();
	}	

}

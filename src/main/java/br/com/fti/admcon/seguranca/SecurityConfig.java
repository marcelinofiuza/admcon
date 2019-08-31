package br.com.fti.admcon.seguranca;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/****************************************************************************
 * Classe de configuração da segurança do software:
 * 
 * @author Bob-Odin - 06/02/2017
 ****************************************************************************/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityUserService securityUserService;

	/****************************************************************************
	 * Monta configuração para autenticação do usuário
	 ****************************************************************************/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		try {

			http
				.headers()
					.frameOptions()
					.sameOrigin()
				.and()
					.formLogin().loginPage("/Login.xhtml").permitAll()
					.failureUrl("/Login.xhtml?error").permitAll()
					.defaultSuccessUrl("/Inicio.xhtml?ok")
				.and()
					.logout().permitAll()
				.and()
					.exceptionHandling()
						.accessDeniedPage("/AcessoNegado.xhtml")
				.and().csrf().disable().authorizeRequests()
					.antMatchers("/javax.faces.resource/**").permitAll()
					.antMatchers("/resources/resvut42/**").permitAll()
					.antMatchers("/WEB-INF/**").permitAll()					
					
					.antMatchers("/Banco.xhtml").hasAnyRole("ADMIN","BANCO")
					.antMatchers("/Cliente.xhtml").hasAnyRole("ADMIN","CLIENTE")
					.antMatchers("/Fornecedor.xhtml").hasAnyRole("ADMIN","FORNECEDOR")
					.antMatchers("/Conta.xhtml").hasAnyRole("ADMIN","CONTA")
					
					.antMatchers("/Receber.xhtml").hasAnyRole("ADMIN","CTAS_RECEBER")
					.antMatchers("/Cobranca.xhtml").hasAnyRole("ADMIN","GERAR_COBRANCA")
					.antMatchers("/Boleto.xhtml").hasAnyRole("ADMIN","EMISSAO_BOLETO")
					.antMatchers("/BoletoRetorno.xhtml").hasAnyRole("ADMIN","RETORNO_BOLETO")				
					.antMatchers("/RelReceber.xhtml").hasAnyRole("ADMIN","REL_RECEBER")
					
					.antMatchers("/Pagar.xhtml").hasAnyRole("ADMIN","CTAS_PAGAR")
					.antMatchers("/RelPagar.xhtml").hasAnyRole("ADMIN","REL_PAGAR")
					
					.antMatchers("/Lancamento.xhtml").hasAnyRole("ADMIN","LANCAMENTO")
					.antMatchers("/Extrato.xhtml").hasAnyRole("ADMIN","EXTRATO")

					.antMatchers("/Empresa.xhtml").hasAnyRole("ADMIN","EMPRESA")
					.antMatchers("/Usuario.xhtml").hasAnyRole("ADMIN","USUARIO")					
					.antMatchers("/Migracao.xhtml").hasAnyRole("ADMIN","MIGRACAO")
			
					.anyRequest().authenticated();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(securityUserService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
}

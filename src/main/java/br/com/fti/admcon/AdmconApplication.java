package br.com.fti.admcon;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
public class AdmconApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AdmconApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdmconApplication.class);
	}

	@Bean
	public FixedLocaleResolver localResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}

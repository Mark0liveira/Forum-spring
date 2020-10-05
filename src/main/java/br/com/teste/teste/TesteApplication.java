package br.com.teste.teste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
// Caso queira o .war extender o metodo com SpringBootServletInitializer
public class TesteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TesteApplication.class, args);
	}

	// Caso queira o .war fazr override deste metodo
	//	@Override
	//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	//		return builder.sources(TesteApplication.class);
	//	}

}

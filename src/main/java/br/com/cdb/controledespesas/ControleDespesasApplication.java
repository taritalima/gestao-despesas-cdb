package br.com.cdb.controledespesas;

import br.com.cdb.controledespesas.config.SwaggerDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ControleDespesasApplication implements SwaggerDoc {

	public static void main(String[] args) {
		SpringApplication.run(ControleDespesasApplication.class, args);
	}

}

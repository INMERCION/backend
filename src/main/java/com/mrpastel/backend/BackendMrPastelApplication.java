package com.mrpastel.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// OpenAPIDefinition movida a SwaggerConfig.java para evitar conflictos
public class BackendMrPastelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendMrPastelApplication.class, args);
	}

}

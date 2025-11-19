package com.mrpastel.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "API Mr. Pastel",
        version = "1.0",
        description = "API REST completa con JWT, CRUD y Base de Datos"
    ),
    servers = {
        // Usar siempre el mismo host desde donde se carga Swagger
        @Server(url = "/", description = "Servidor actual")
    }
)

@SpringBootApplication
public class BackendMrPastelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendMrPastelApplication.class, args);
	}

}

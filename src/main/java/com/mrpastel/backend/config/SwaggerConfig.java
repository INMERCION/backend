package com.mrpastel.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API REST Mr. Pastel",
        version = "2.0.0",
        description = "API REST completa con JWT, CRUD y Base de Datos. " +
                      "Usa el botón 'Authorize' para autenticarte con el token JWT.",
        contact = @Contact(
            name = "Equipo Mr. Pastel",
            email = "contacto@mrpastel.cl"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Servidor de Desarrollo Local"
        ),
        @Server(
            url = "http://98.91.93.249:8080",
            description = "Servidor EC2 - Producción"
        )
    },
    security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Ingresa el token JWT obtenido desde /api/auth/login (SIN el prefijo 'Bearer')",
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    // Esta clase solo define la configuración OpenAPI
    // No necesita métodos adicionales
}

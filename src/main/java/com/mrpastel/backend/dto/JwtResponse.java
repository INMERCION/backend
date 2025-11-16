package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con JWT")
public class JwtResponse {
    @Schema(example = "eyJhbGciOiJIUzUxMiJ9...")
    private String token;

    @Schema(example = "BEARER")
    private String type;

    @Schema(example = "1")
    private Long id;

    @Schema(example = "usuario@example.com")
    private String email;

    @Schema(example = "Juan Pérez")
    private String nombre;

    @Schema(example = "12.345.678-9")
    private String rut;

    @Schema(example = "USUARIO")
    private String rol;

    public JwtResponse(String token, Long id, String email, String nombre, String rut, String rol) {
        this.token = token;
        this.type = "Bearer";
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rut = rut;
        this.rol = rol;
    }
}

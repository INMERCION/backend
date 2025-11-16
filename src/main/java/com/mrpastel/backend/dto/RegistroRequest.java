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
@Schema(description = "DTO para Registro de usuario")
public class RegistroRequest {
    @Schema(example = "Juan Pérez")
    private String nombre;

    @Schema(example = "juan@example.com")
    private String email;

    @Schema(example = "12.345.678-9")
    private String rut;

    @Schema(example = "password123")
    private String password;
}

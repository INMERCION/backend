package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Usuario")
public class UsuarioDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Juan Pérez")
    private String nombre;

    @Schema(example = "juan@example.com")
    private String email;

    @Schema(example = "12.345.678-9")
    private String rut;

    @Schema(example = "USUARIO")
    private String rol;

    private LocalDateTime fechaCreacion;
}

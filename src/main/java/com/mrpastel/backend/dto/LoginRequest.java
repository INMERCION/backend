package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Login")
public class LoginRequest {
    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es requerido")
    @Schema(example = "usuario@example.com")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    @Schema(example = "password123")
    private String password;
}

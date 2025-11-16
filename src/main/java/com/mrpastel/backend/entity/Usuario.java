package com.mrpastel.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es requerido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    private String password;

    @Column(unique = true)
    private String rut;

    private String rol;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    private void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.rol == null) {
            this.rol = "USUARIO";
        }
    }
}

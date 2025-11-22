package com.mrpastel.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es requerido")
    private String nombre;

    @NotBlank(message = "La descripción es requerida")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    private String categoria;

    @Positive(message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    private String imagen;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    private void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}

package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Producto")
public class ProductoDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Torta de Chocolate")
    private String nombre;

    @Schema(example = "Deliciosa torta de chocolate con cobertura de ganache")
    private String descripcion;

    @Schema(example = "45.00")
    private BigDecimal precio;

    @Schema(example = "Tortas")
    private String categoria;

    @Schema(example = "10")
    private Integer stock;

    @Schema(example = "https://example.com/imagen.jpg")
    private String imagen;

    @Schema(example = "CALI")
    private String region;

    private LocalDateTime fechaCreacion;
}

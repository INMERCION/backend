package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Item del Carrito")
public class ItemCarritoDTO {
    @Schema(example = "1")
    private Long id;

    private ProductoDTO producto;

    @Schema(example = "2")
    private Integer cantidad;

    private BigDecimal subtotal;
}

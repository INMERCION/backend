package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Carrito")
public class CarritoDTO {
    @Schema(example = "1")
    private Long id;

    private List<ItemCarritoDTO> items;

    private BigDecimal total;
}

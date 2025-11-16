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
@Schema(description = "DTO para Item de Pedido")
public class PedidoItemDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long productoId;

    private String nombre;
    private String imagen;

    @Schema(example = "2")
    private Integer cantidad;

    @Schema(example = "15000.0")
    private BigDecimal precio;
}

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
@Schema(description = "DTO para crear un Pedido")
public class CrearPedidoRequest {
    @Schema(example = "1")
    private Long usuarioId;

    @Schema(example = "45000.0")
    private BigDecimal total;

    private List<ItemPedidoRequest> productos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemPedidoRequest {
        @Schema(example = "1")
        private Long productoId;

        @Schema(example = "2")
        private Integer cantidad;

        @Schema(example = "15000.0")
        private BigDecimal precio;
    }
}

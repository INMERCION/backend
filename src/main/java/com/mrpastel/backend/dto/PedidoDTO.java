package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para Pedido")
public class PedidoDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long usuarioId;

    private String usuarioNombre;

    @Schema(example = "45000.0")
    private BigDecimal total;

    @Schema(example = "procesando")
    private String estado;

    private LocalDateTime fechaCreacion;

    private List<PedidoItemDTO> productos;
}

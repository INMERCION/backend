package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear un intento de pago con Stripe")
public class PaymentIntentRequest {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @Schema(description = "Monto del pago", example = "50000.0")
    private BigDecimal amount;

    @Schema(description = "Moneda (clp, usd, etc.)", example = "clp", defaultValue = "clp")
    private String currency;

    @Schema(description = "Descripción del pago", example = "Pedido #123 - Torta de chocolate")
    private String description;

    @Schema(description = "ID del pedido asociado", example = "123")
    private Long pedidoId;

    @Schema(description = "Email del cliente", example = "cliente@ejemplo.com")
    private String customerEmail;
}

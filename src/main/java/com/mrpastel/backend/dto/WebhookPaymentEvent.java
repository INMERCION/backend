package com.mrpastel.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Evento de webhook de Stripe")
public class WebhookPaymentEvent {

    @Schema(description = "ID del PaymentIntent")
    private String paymentIntentId;

    @Schema(description = "Estado del pago")
    private String status;

    @Schema(description = "Tipo de evento")
    private String eventType;

    @Schema(description = "ID del pedido asociado")
    private Long pedidoId;

    @Schema(description = "Monto pagado")
    private Long amount;
}

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
@Schema(description = "Respuesta de Stripe con el client secret")
public class PaymentResponse {

    @Schema(description = "Client Secret de Stripe para completar el pago en frontend")
    private String clientSecret;

    @Schema(description = "ID del PaymentIntent")
    private String paymentIntentId;

    @Schema(description = "Estado del pago")
    private String status;

    @Schema(description = "Monto del pago")
    private Long amount;

    @Schema(description = "Moneda")
    private String currency;
}

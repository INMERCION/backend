package com.mrpastel.backend.service;

import com.mrpastel.backend.dto.PaymentIntentRequest;
import com.mrpastel.backend.dto.PaymentResponse;
import com.mrpastel.backend.dto.WebhookPaymentEvent;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Value("${stripe.currency:clp}")
    private String defaultCurrency;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
        log.info("Stripe inicializado correctamente");
    }

    /**
     * Crea un PaymentIntent en Stripe
     */
    public PaymentResponse createPaymentIntent(PaymentIntentRequest request) {
        try {
            // Convertir el monto a centavos (Stripe trabaja en centavos)
            long amountInCents = request.getAmount().multiply(BigDecimal.valueOf(100)).longValue();

            // Configurar metadata para tracking
            Map<String, String> metadata = new HashMap<>();
            if (request.getPedidoId() != null) {
                metadata.put("pedido_id", request.getPedidoId().toString());
            }
            if (request.getDescription() != null) {
                metadata.put("description", request.getDescription());
            }

            // Crear el PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(request.getCurrency() != null ? request.getCurrency() : defaultCurrency)
                    .setDescription(request.getDescription())
                    .putAllMetadata(metadata)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            log.info("PaymentIntent creado: {} con monto: {}", paymentIntent.getId(), amountInCents);

            return PaymentResponse.builder()
                    .clientSecret(paymentIntent.getClientSecret())
                    .paymentIntentId(paymentIntent.getId())
                    .status(paymentIntent.getStatus())
                    .amount(paymentIntent.getAmount())
                    .currency(paymentIntent.getCurrency())
                    .build();

        } catch (StripeException e) {
            log.error("Error al crear PaymentIntent: {}", e.getMessage());
            throw new RuntimeException("Error al procesar el pago: " + e.getMessage());
        }
    }

    /**
     * Obtiene información de un PaymentIntent
     */
    public PaymentIntent getPaymentIntent(String paymentIntentId) {
        try {
            return PaymentIntent.retrieve(paymentIntentId);
        } catch (StripeException e) {
            log.error("Error al obtener PaymentIntent {}: {}", paymentIntentId, e.getMessage());
            throw new RuntimeException("Error al obtener información del pago");
        }
    }

    /**
     * Cancela un PaymentIntent
     */
    public void cancelPaymentIntent(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            paymentIntent.cancel();
            log.info("PaymentIntent cancelado: {}", paymentIntentId);
        } catch (StripeException e) {
            log.error("Error al cancelar PaymentIntent {}: {}", paymentIntentId, e.getMessage());
            throw new RuntimeException("Error al cancelar el pago");
        }
    }

    /**
     * Procesa webhooks de Stripe
     */
    public WebhookPaymentEvent processWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            log.info("Webhook recibido: {} - {}", event.getType(), event.getId());

            // Procesar según el tipo de evento
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    return handlePaymentSuccess(event);
                case "payment_intent.payment_failed":
                    return handlePaymentFailed(event);
                case "payment_intent.canceled":
                    return handlePaymentCanceled(event);
                default:
                    log.info("Evento no manejado: {}", event.getType());
                    return null;
            }

        } catch (SignatureVerificationException e) {
            log.error("Error de verificación de firma del webhook: {}", e.getMessage());
            throw new RuntimeException("Firma del webhook inválida");
        }
    }

    private WebhookPaymentEvent handlePaymentSuccess(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject().orElse(null);

        if (paymentIntent != null) {
            log.info("Pago exitoso: {} - Monto: {}", paymentIntent.getId(), paymentIntent.getAmount());

            Long pedidoId = null;
            if (paymentIntent.getMetadata().containsKey("pedido_id")) {
                pedidoId = Long.parseLong(paymentIntent.getMetadata().get("pedido_id"));
            }

            return WebhookPaymentEvent.builder()
                    .paymentIntentId(paymentIntent.getId())
                    .status("succeeded")
                    .eventType("payment_intent.succeeded")
                    .pedidoId(pedidoId)
                    .amount(paymentIntent.getAmount())
                    .build();
        }
        return null;
    }

    private WebhookPaymentEvent handlePaymentFailed(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject().orElse(null);

        if (paymentIntent != null) {
            log.warn("Pago fallido: {}", paymentIntent.getId());

            Long pedidoId = null;
            if (paymentIntent.getMetadata().containsKey("pedido_id")) {
                pedidoId = Long.parseLong(paymentIntent.getMetadata().get("pedido_id"));
            }

            return WebhookPaymentEvent.builder()
                    .paymentIntentId(paymentIntent.getId())
                    .status("failed")
                    .eventType("payment_intent.payment_failed")
                    .pedidoId(pedidoId)
                    .amount(paymentIntent.getAmount())
                    .build();
        }
        return null;
    }

    private WebhookPaymentEvent handlePaymentCanceled(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject().orElse(null);

        if (paymentIntent != null) {
            log.info("Pago cancelado: {}", paymentIntent.getId());

            Long pedidoId = null;
            if (paymentIntent.getMetadata().containsKey("pedido_id")) {
                pedidoId = Long.parseLong(paymentIntent.getMetadata().get("pedido_id"));
            }

            return WebhookPaymentEvent.builder()
                    .paymentIntentId(paymentIntent.getId())
                    .status("canceled")
                    .eventType("payment_intent.canceled")
                    .pedidoId(pedidoId)
                    .amount(paymentIntent.getAmount())
                    .build();
        }
        return null;
    }
}

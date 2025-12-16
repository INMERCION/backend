package com.mrpastel.backend.controller;

import com.mrpastel.backend.dto.PaymentIntentRequest;
import com.mrpastel.backend.dto.PaymentResponse;
import com.mrpastel.backend.dto.WebhookPaymentEvent;
import com.mrpastel.backend.service.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Gestión de Pagos con Stripe")
@CrossOrigin(origins = "*")
@Slf4j
public class PagoController {

    @Autowired
    private StripeService stripeService;

    @Value("${stripe.publishable.key}")
    private String publishableKey;

    @PostMapping("/create-payment-intent")
    @Operation(summary = "Crear un PaymentIntent para iniciar el pago")
    public ResponseEntity<?> createPaymentIntent(@Valid @RequestBody PaymentIntentRequest request) {
        try {
            PaymentResponse response = stripeService.createPaymentIntent(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al crear PaymentIntent: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/payment-intent/{id}")
    @Operation(summary = "Obtener información de un PaymentIntent")
    public ResponseEntity<?> getPaymentIntent(@PathVariable String id) {
        try {
            var paymentIntent = stripeService.getPaymentIntent(id);
            Map<String, Object> response = new HashMap<>();
            response.put("id", paymentIntent.getId());
            response.put("status", paymentIntent.getStatus());
            response.put("amount", paymentIntent.getAmount());
            response.put("currency", paymentIntent.getCurrency());
            response.put("metadata", paymentIntent.getMetadata());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al obtener PaymentIntent: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping("/cancel-payment-intent/{id}")
    @Operation(summary = "Cancelar un PaymentIntent")
    public ResponseEntity<?> cancelPaymentIntent(@PathVariable String id) {
        try {
            stripeService.cancelPaymentIntent(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Pago cancelado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error al cancelar PaymentIntent: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/webhook")
    @Operation(summary = "Webhook para recibir eventos de Stripe")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            WebhookPaymentEvent event = stripeService.processWebhook(payload, sigHeader);

            if (event != null) {
                log.info("Evento procesado: {} para PaymentIntent: {}",
                        event.getEventType(), event.getPaymentIntentId());

                // Aquí puedes actualizar el estado del pedido según el evento
                if ("payment_intent.succeeded".equals(event.getEventType()) && event.getPedidoId() != null) {
                    // TODO: Actualizar estado del pedido a "PAGADO"
                    log.info("Pedido {} pagado exitosamente", event.getPedidoId());
                }
            }

            return ResponseEntity.ok("Webhook procesado");
        } catch (RuntimeException e) {
            log.error("Error al procesar webhook: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/config")
    @Operation(summary = "Obtener configuración pública de Stripe")
    public ResponseEntity<Map<String, String>> getConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("publishableKey", publishableKey);
        return ResponseEntity.ok(config);
    }
}

# 🎉 Integración de Stripe - Guía Completa

## 📋 Resumen
Se ha implementado Stripe como pasarela de pago en tu aplicación Mr. Pastel, permitiendo procesar pagos de forma segura usando tarjetas de crédito/débito.

---

## 🏗️ Archivos Creados

### Backend (Spring Boot)
- ✅ `StripeService.java` - Servicio principal con lógica de Stripe
- ✅ `PagoController.java` - Endpoints REST para pagos
- ✅ `PaymentIntentRequest.java` - DTO para crear pagos
- ✅ `PaymentResponse.java` - DTO de respuesta
- ✅ `WebhookPaymentEvent.java` - DTO para webhooks

### Frontend (React)
- ✅ `CheckoutStripe.jsx` - Componente de pago con Stripe Elements
- ✅ `PagoConfirmacion.jsx` - Página de confirmación de pago
- ✅ `PagoExitoso.jsx` - Página de pago exitoso
- ✅ CSS correspondiente para todos los componentes

### Configuración
- ✅ Dependencia Stripe agregada al `pom.xml`
- ✅ Propiedades agregadas a `application.properties`
- ✅ Dependencias de Stripe agregadas al `package.json`

---

## 🚀 Configuración Paso a Paso

### 1️⃣ Crear Cuenta en Stripe

1. Ve a [https://stripe.com](https://stripe.com)
2. Crea una cuenta gratuita
3. Activa el "Modo de Prueba" (Test Mode)
4. Ve a **Developers > API keys**

### 2️⃣ Configurar Backend

Edita `backend/src/main/resources/application.properties`:

```properties
# Stripe Payment Gateway
stripe.api.key=sk_test_TU_SECRET_KEY_AQUI
stripe.webhook.secret=whsec_TU_WEBHOOK_SECRET_AQUI
stripe.currency=clp
```

**Importante:** 
- `sk_test_...` es tu **Secret Key** (nunca la expongas en frontend)
- `whsec_...` lo obtienes al configurar el webhook (paso 4)

También actualiza en `PagoController.java` línea 108:
```java
config.put("publishableKey", "pk_test_TU_PUBLISHABLE_KEY_AQUI");
```

### 3️⃣ Configurar Frontend

Edita `mr-pastel-react/src/components/CheckoutStripe.jsx` línea 11:

```javascript
const stripePromise = loadStripe('pk_test_TU_PUBLISHABLE_KEY_AQUI');
```

**Importante:** `pk_test_...` es tu **Publishable Key** (es pública, puedes exponerla)

### 4️⃣ Instalar Dependencias

**Backend:**
```bash
cd backend
mvn clean install
```

**Frontend:**
```bash
cd mr-pastel-react
npm install
```

### 5️⃣ Configurar Webhooks (Opcional pero Recomendado)

Los webhooks permiten que Stripe notifique a tu backend cuando un pago es exitoso.

1. Ve a **Developers > Webhooks** en Stripe Dashboard
2. Click en **Add endpoint**
3. URL del endpoint: `http://localhost:8080/api/pagos/webhook`
4. Selecciona estos eventos:
   - `payment_intent.succeeded`
   - `payment_intent.payment_failed`
   - `payment_intent.canceled`
5. Copia el **Signing secret** (`whsec_...`) y pégalo en `application.properties`

**Para producción:** Usa tu dominio real, ej: `https://tudominio.com/api/pagos/webhook`

---

## 🎯 Cómo Usar

### Desde el Carrito

1. Modifica `CarritoBackend.jsx` para redirigir a la página de pago:

```jsx
const handleFinalizarCompra = () => {
  // Guardar info del pedido
  localStorage.setItem('pedidoPago', JSON.stringify({
    pedidoId: null, // Se obtiene después de crear el pedido
    amount: total,
    description: `Pedido Mr. Pastel - ${productos.length} productos`
  }));
  
  // Redirigir a pago
  navigate('/pago');
};
```

2. Agrega las rutas en `App.jsx`:

```jsx
import PagoConfirmacion from './pages/PagoConfirmacion';
import PagoExitoso from './pages/PagoExitoso';

// En tus rutas:
<Route path="/pago" element={<PagoConfirmacion />} />
<Route path="/pago-confirmado" element={<PagoExitoso />} />
```

### Flujo Completo

1. Usuario agrega productos al carrito
2. Click en "Finalizar Compra"
3. Se crea el pedido en backend
4. Se guarda info en localStorage
5. Redirige a `/pago`
6. Se crea el PaymentIntent en Stripe
7. Usuario ingresa datos de tarjeta
8. Stripe procesa el pago
9. Redirige a `/pago-confirmado`
10. Webhook actualiza estado del pedido

---

## 🧪 Tarjetas de Prueba

Stripe proporciona estas tarjetas para testing:

| Tarjeta | Resultado |
|---------|-----------|
| `4242 4242 4242 4242` | ✅ Pago exitoso |
| `4000 0000 0000 9995` | ❌ Fondos insuficientes |
| `4000 0000 0000 9987` | ❌ Tarjeta rechazada |
| `4000 0025 0000 3155` | ✅ Requiere autenticación 3D Secure |

**Datos adicionales:**
- Fecha de vencimiento: Cualquier fecha futura (ej: 12/25)
- CVC: Cualquier 3 dígitos (ej: 123)
- ZIP: Cualquier 5 dígitos (ej: 12345)

---

## 📡 Endpoints API

### `POST /api/pagos/create-payment-intent`
Crea un nuevo intento de pago.

**Request:**
```json
{
  "amount": 50000,
  "currency": "clp",
  "description": "Pedido #123",
  "pedidoId": 123,
  "customerEmail": "cliente@ejemplo.com"
}
```

**Response:**
```json
{
  "clientSecret": "pi_xxx_secret_xxx",
  "paymentIntentId": "pi_xxxxxxxxxxxxx",
  "status": "requires_payment_method",
  "amount": 5000000,
  "currency": "clp"
}
```

### `GET /api/pagos/payment-intent/{id}`
Obtiene información de un pago.

### `POST /api/pagos/cancel-payment-intent/{id}`
Cancela un intento de pago.

### `POST /api/pagos/webhook`
Recibe notificaciones de Stripe (usa Stripe-Signature header).

---

## 🔒 Seguridad

✅ **Buenas prácticas implementadas:**
- Secret Key solo en backend
- Validación de webhooks con firma
- Manejo de errores apropiado
- Logs de todas las transacciones

⚠️ **Antes de producción:**
1. Usa HTTPS (requerido por Stripe)
2. Reemplaza las keys de test (`sk_test_`, `pk_test_`) por las de producción (`sk_live_`, `pk_live_`)
3. Configura webhooks en tu dominio real
4. Agrega validaciones adicionales (usuario autenticado, stock disponible, etc.)

---

## 🔗 Integración con Pedidos

Para actualizar automáticamente el estado de un pedido cuando el pago sea exitoso, modifica el método `handlePaymentSuccess` en el webhook del `PagoController`:

```java
if ("payment_intent.succeeded".equals(event.getEventType()) && event.getPedidoId() != null) {
    // Inyectar PedidoService
    pedidoService.actualizarEstado(event.getPedidoId(), "PAGADO");
    log.info("Pedido {} actualizado a PAGADO", event.getPedidoId());
}
```

---

## 📚 Documentación Adicional

- [Stripe Docs](https://stripe.com/docs)
- [Stripe Java SDK](https://stripe.com/docs/api/java)
- [Stripe React Elements](https://stripe.com/docs/stripe-js/react)
- [Testing con Stripe](https://stripe.com/docs/testing)

---

## 🐛 Solución de Problemas

### Error: "No such payment_intent"
- Verifica que el PaymentIntent fue creado correctamente
- Revisa los logs del backend

### Error: "Invalid API Key"
- Verifica que copiaste correctamente las keys
- Asegúrate de usar `sk_test_` en backend y `pk_test_` en frontend

### El webhook no funciona
- Verifica la URL del webhook en Stripe Dashboard
- Usa ngrok para testing local: `ngrok http 8080`
- Copia la URL de ngrok como webhook en Stripe

### Pago no se completa
- Revisa la consola del navegador (F12)
- Verifica que el backend esté corriendo en puerto 8080
- Asegúrate de tener CORS habilitado

---

## ✅ Checklist de Implementación

- [ ] Crear cuenta en Stripe
- [ ] Copiar API keys a application.properties
- [ ] Copiar Publishable key a CheckoutStripe.jsx y PagoController.java
- [ ] Instalar dependencias backend (`mvn clean install`)
- [ ] Instalar dependencias frontend (`npm install`)
- [ ] Agregar rutas en App.jsx
- [ ] Modificar CarritoBackend.jsx para redirigir a /pago
- [ ] Probar con tarjeta de prueba 4242 4242 4242 4242
- [ ] Configurar webhooks (opcional)
- [ ] Integrar actualización de estado de pedidos

---

## 🎊 ¡Listo!

Tu aplicación ahora puede procesar pagos con Stripe. Puedes probarla inmediatamente usando las tarjetas de prueba.

**Para iniciar:**
```bash
# Terminal 1 - Backend
cd backend
mvn spring-boot:run

# Terminal 2 - Frontend
cd mr-pastel-react
npm start
```

¡Happy coding! 🚀🍰

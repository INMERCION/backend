# 🚀 INICIO RÁPIDO - Stripe Integrado

## ✅ TODO LISTO PARA USAR

---

## 🎯 Iniciar la Aplicación

### Opción 1: Usar Scripts (Recomendado)
```bash
# Windows - Doble click en:
iniciar-con-stripe.bat

# O desde PowerShell:
./iniciar-con-stripe.ps1
```

### Opción 2: Manual
**Terminal 1 - Backend:**
```bash
cd backend
./mvnw.cmd spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd mr-pastel-react
npm start
```

---

## 🧪 Probar el Pago

1. **Abrir:** http://localhost:3000
2. **Iniciar sesión** con tu usuario
3. **Agregar productos** al carrito
4. Click en **"Proceder al Pago con Stripe"**
5. **Tarjeta de prueba:**
   - Número: `4242 4242 4242 4242`
   - Fecha: `12/25` (cualquier fecha futura)
   - CVC: `123` (cualquier 3 dígitos)
   - Nombre: Tu nombre
6. Click en **"Pagar"**
7. ¡Ver confirmación de pago exitoso! 🎉

---

## 📱 URLs de la Aplicación

| Servicio | URL |
|----------|-----|
| **Frontend** | http://localhost:3000 |
| **Backend API** | http://localhost:8080 |
| **Swagger Docs** | http://localhost:8080/swagger-ui.html |
| **API Pagos** | http://localhost:8080/api/pagos |

---

## 🔑 Configuración Actual

✅ **Backend:**
- Stripe Java SDK v25.0.0
- Secret Key configurada
- Publishable Key configurada
- Moneda: CLP (Pesos Chilenos)

✅ **Frontend:**
- @stripe/stripe-js v4.10.0
- @stripe/react-stripe-js v2.9.0
- Publishable Key configurada
- Componentes de pago listos

---

## 🎨 Componentes Creados

### Backend
- `StripeService.java` - Lógica de pagos
- `PagoController.java` - Endpoints REST
- `PaymentIntentRequest.java` - DTO request
- `PaymentResponse.java` - DTO response
- `WebhookPaymentEvent.java` - DTO webhook

### Frontend
- `CheckoutStripe.jsx` - Formulario de pago
- `PagoConfirmacion.jsx` - Página pre-pago
- `PagoExitoso.jsx` - Página confirmación
- Estilos CSS para todos los componentes

---

## 📡 Endpoints Disponibles

```http
POST   /api/pagos/create-payment-intent
GET    /api/pagos/payment-intent/{id}
POST   /api/pagos/cancel-payment-intent/{id}
POST   /api/pagos/webhook
GET    /api/pagos/config
```

---

## 🧪 Más Tarjetas de Prueba

| Tarjeta | Resultado |
|---------|-----------|
| `4242 4242 4242 4242` | ✅ Exitoso |
| `4000 0000 0000 9995` | ❌ Sin fondos |
| `4000 0000 0000 9987` | ❌ Rechazada |
| `4000 0025 0000 3155` | 🔒 Requiere 3D Secure |

---

## 🔧 Solución de Problemas

### Backend no inicia
```bash
# Verificar Java
java -version

# Limpiar y recompilar
cd backend
./mvnw.cmd clean compile
```

### Frontend no inicia
```bash
# Reinstalar dependencias
cd mr-pastel-react
npm install --legacy-peer-deps
```

### Error de conexión con Stripe
- ✅ Verifica que las keys en `application.properties` sean correctas
- ✅ Verifica que la publishable key en `CheckoutStripe.jsx` sea correcta
- ✅ Asegúrate de tener conexión a internet

### Error en el pago
- ✅ Usa la tarjeta de prueba: 4242 4242 4242 4242
- ✅ Verifica la consola del navegador (F12)
- ✅ Verifica los logs del backend

---

## 📚 Documentación Completa

- **Guía Detallada:** [GUIA_STRIPE.md](GUIA_STRIPE.md)
- **Resumen Completo:** [INTEGRACION_STRIPE_COMPLETA.md](INTEGRACION_STRIPE_COMPLETA.md)
- **Stripe Docs:** https://stripe.com/docs

---

## 🎊 Estado de la Integración

| Componente | Estado |
|------------|--------|
| Backend Compilado | ✅ |
| Frontend Compilado | ✅ |
| Dependencias Instaladas | ✅ |
| Rutas Configuradas | ✅ |
| Carrito Integrado | ✅ |
| Pagos Funcionando | ✅ |

---

## ⚡ Siguiente Paso

```bash
# Ejecuta este comando para iniciar todo:
./iniciar-con-stripe.bat

# O manualmente:
# Terminal 1:
cd backend && ./mvnw.cmd spring-boot:run

# Terminal 2:
cd mr-pastel-react && npm start
```

**¡Ya puedes empezar a probar pagos con Stripe!** 🚀🎂

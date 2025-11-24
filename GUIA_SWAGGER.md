# 🚀 Guía Completa para Probar APIs en Swagger

**Versión**: 1.0  
**Fecha**: Noviembre 23, 2025  
**Backend**: Mr. Pastel API REST

---

## 📋 Tabla de Contenidos

1. [Prerequisitos](#prerequisitos)
2. [Iniciar el Backend](#iniciar-el-backend)
3. [Acceder a Swagger](#acceder-a-swagger)
4. [Endpoints Públicos (Sin Token)](#endpoints-públicos)
5. [Autenticación y Token](#autenticación-y-token)
6. [Endpoints de Usuario](#endpoints-de-usuario)
7. [Endpoints de Admin](#endpoints-de-admin)
8. [Solución de Problemas](#solución-de-problemas)

---

## ✅ Prerequisitos

Antes de comenzar, asegúrate de tener:

- ✅ Java 17 o superior instalado
- ✅ Maven instalado
- ✅ MySQL corriendo en `localhost:3306`
- ✅ Base de datos `mr_pastel` creada
- ✅ Backend configurado correctamente

---

## 🚀 Iniciar el Backend

### Opción 1: Desde la Terminal

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Opción 2: Desde VS Code / IntelliJ

1. Abre el proyecto `backend`
2. Busca `BackendMrPastelApplication.java`
3. Click derecho → Run

### Verificar que está corriendo

Deberías ver en la consola:
```
Started BackendMrPastelApplication in X.XXX seconds
```

Verifica en tu navegador:
- **API Base**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html

---

## 🌐 Acceder a Swagger

1. Abre tu navegador (Chrome, Firefox, Edge)
2. Ve a: **http://localhost:8080/swagger-ui.html**
3. Verás la interfaz de Swagger con 6 secciones:
   - 🔐 **Autenticación**
   - 👥 **Usuarios**
   - 📦 **Productos**
   - 📋 **Pedidos**
   - 🛒 **Carrito**
   - 📧 **Contacto**

---

## 🔓 Endpoints Públicos (Sin Token)

Estos endpoints NO requieren autenticación.

### 1️⃣ Ver Todos los Productos

**Pasos:**
1. Expande la sección **"Productos"** (click en la flecha)
2. Busca `GET /api/productos`
3. Click en el endpoint para expandirlo
4. Click en el botón azul **"Try it out"**
5. Click en **"Execute"**

**Resultado esperado:**
```json
[
  {
    "id": 1,
    "nombre": "Torta de Chocolate",
    "descripcion": "Deliciosa torta de chocolate",
    "precio": 45000,
    "categoria": "Tortas",
    "stock": 10,
    "imagen": "/images/torta-chocolate.jpg",
    "fechaCreacion": "2025-11-23T10:00:00"
  }
]
```

### 2️⃣ Ver un Producto Específico

**Pasos:**
1. Busca `GET /api/productos/{id}`
2. Click en **"Try it out"**
3. En el campo `id`, ingresa: `1`
4. Click en **"Execute"**

**Resultado esperado:**
```json
{
  "id": 1,
  "nombre": "Torta de Chocolate",
  "descripcion": "Deliciosa torta de chocolate",
  "precio": 45000,
  "categoria": "Tortas",
  "stock": 10,
  "imagen": "/images/torta-chocolate.jpg",
  "fechaCreacion": "2025-11-23T10:00:00"
}
```

### 3️⃣ Buscar Productos por Nombre

**Pasos:**
1. Busca `GET /api/productos/buscar`
2. Click en **"Try it out"**
3. En el campo `nombre`, ingresa: `chocolate`
4. Click en **"Execute"**

**Resultado:** Lista de productos que contengan "chocolate" en el nombre.

### 4️⃣ Productos por Categoría

**Pasos:**
1. Busca `GET /api/productos/categoria/{categoria}`
2. Click en **"Try it out"**
3. En el campo `categoria`, ingresa: `Tortas`
4. Click en **"Execute"**

**Categorías disponibles:**
- Tortas
- Postres
- Sin Azúcar
- Panadería
- Sin Gluten
- Veganos
- Especiales

### 5️⃣ Registrar un Nuevo Usuario

**Pasos:**
1. Expande la sección **"Autenticación"**
2. Busca `POST /api/auth/registro`
3. Click en **"Try it out"**
4. Modifica el JSON:
   ```json
  {
     "nombre": "Test Usuario",
     "email": "test@ejemplo.com",
     "password": "test123",
     "rut": "19.876.543-K"
   }
   ```
5. Click en **"Execute"**

**Resultado esperado (201):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "id": 2,
  "email": "test@ejemplo.com",
  "nombre": "Test Usuario",
  "rut": "12.345.678-9",
  "rol": "USUARIO"
}
```

⚠️ **Guarda el token**, lo necesitarás para endpoints protegidos.

### 6️⃣ Enviar Mensaje de Contacto

**Pasos:**
1. Expande la sección **"Contacto"**
2. Busca `POST /api/contacto`
3. Click en **"Try it out"**
4. Modifica el JSON:
   ```json
   {
     "nombre": "Juan Pérez",
     "email": "juan@ejemplo.com",
     "asunto": "Consulta",
     "mensaje": "Quiero saber sobre las tortas"
   }
   ```
5. Click en **"Execute"**

---

## 🔐 Autenticación y Token

Para acceder a endpoints protegidos, necesitas:
1. Hacer login
2. Obtener un token
3. Configurar el token en Swagger

### Paso 1: Hacer Login

**Pasos:**
1. Expande **"Autenticación"**
2. Busca `POST /api/auth/login`
3. Click en **"Try it out"**
4. Ingresa las credenciales del admin:
   ```json
   {
     "email": "admin@mrpastel.cl",
     "password": "admin123"
   }
   ```
5. Click en **"Execute"**

**Resultado esperado (200):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBtcnBhc3RlbC5jbCIsInJvbCI6IkFETUlOIiwiaWF0IjoxNzAwNzYwMDAwLCJleHAiOjE3MDA4NDY0MDB9.abcdefg123456...",
  "id": 1,
  "email": "admin@mrpastel.cl",
  "nombre": "Administrador",
  "rut": "11.111.111-1",
  "rol": "ADMIN"
}
```

### Paso 2: Copiar el Token

Selecciona y copia TODO el valor del campo `token`. Ejemplo:
```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBtcnBhc3RlbC5jbCIsInJvbCI6IkFETUlOIiwiaWF0IjoxNzAwNzYwMDAwLCJleHAiOjE3MDA4NDY0MDB9.abcdefg123456...
```

### Paso 3: Configurar Token en Swagger

1. **Busca** el botón **"Authorize" 🔓** en la parte superior derecha de Swagger
2. **Click** en **"Authorize"**
3. Aparecerá un modal con un campo **"Value"**
4. **Escribe** (SIN COMILLAS):
   ```
   Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBtcnBhc3RlbC5jbCIsInJvbCI6IkFETUlOIiwiaWF0IjoxNzAwNzYwMDAwLCJleHAiOjE3MDA4NDY0MDB9.abcdefg123456...
   ```
   ⚠️ **IMPORTANTE:** Debe empezar con `Bearer ` (con espacio)
5. **Click** en **"Authorize"**
6. **Click** en **"Close"**
7. **Verás** que el candado cambió a 🔒 (cerrado)

✅ **¡Listo!** Ahora puedes usar endpoints protegidos.

---

## 👤 Endpoints de Usuario (Requieren Token)

### 1️⃣ Ver Tu Carrito

**Pasos:**
1. Asegúrate de estar autenticado (candado cerrado 🔒)
2. Expande **"Carrito"**
3. Busca `GET /api/carrito/{usuarioId}`
4. Click en **"Try it out"**
5. En `usuarioId`, ingresa el ID que obtuviste al hacer login (ejemplo: `1`)
6. Click en **"Execute"**

**Resultado esperado (200):**
```json
{
  "id": 1,
  "items": [],
  "total": 0
}
```

### 2️⃣ Agregar Producto al Carrito

**Pasos:**
1. Busca `POST /api/carrito/{usuarioId}/agregar`
2. Click en **"Try it out"**
3. Completa los campos:
   - `usuarioId`: Tu ID (ejemplo: `1`)
   - `productoId`: ID del producto (ejemplo: `1`)
   - `cantidad`: Cantidad (ejemplo: `2`)
4. Click en **"Execute"**

**Resultado esperado (200):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "producto": {
        "id": 1,
        "nombre": "Torta de Chocolate",
        "precio": 45000
      },
      "cantidad": 2,
      "subtotal": 90000
    }
  ],
  "total": 90000
}
```

### 3️⃣ Actualizar Cantidad de un Item

**Pasos:**
1. Busca `PUT /api/carrito/{usuarioId}/actualizar-item/{itemId}`
2. Click en **"Try it out"**
3. Completa:
   - `usuarioId`: Tu ID
   - `itemId`: ID del item en el carrito
   - `cantidad`: Nueva cantidad (ejemplo: `3`)
4. Click en **"Execute"**

### 4️⃣ Eliminar Item del Carrito

**Pasos:**
1. Busca `DELETE /api/carrito/{usuarioId}/eliminar-item/{itemId}`
2. Click en **"Try it out"**
3. Completa `usuarioId` e `itemId`
4. Click en **"Execute"**

**Resultado:** Código 200 con carrito actualizado.

### 5️⃣ Vaciar Todo el Carrito

**Pasos:**
1. Busca `DELETE /api/carrito/{usuarioId}/vaciar`
2. Click en **"Try it out"**
3. Ingresa `usuarioId`
4. Click en **"Execute"**

**Resultado:** Código 204 (No Content).

### 6️⃣ Crear un Pedido

**Pasos:**
1. Expande **"Pedidos"**
2. Busca `POST /api/pedidos`
3. Click en **"Try it out"**
4. Modifica el JSON:
   ```json
   {
     "usuarioId": 1,
     "direccionEnvio": "Calle Principal 123, Cali",
     "metodoPago": "TARJETA_CREDITO",
     "items": [
       {
         "productoId": 1,
         "cantidad": 2
       }
     ]
   }
   ```
5. Click en **"Execute"**

**Métodos de pago válidos:**
- `TARJETA_CREDITO`
- `TARJETA_DEBITO`
- `PSE`
- `EFECTIVO`
- `TRANSFERENCIA`

**Resultado esperado (201):**
```json
{
  "id": 1,
  "usuarioId": 1,
  "estado": "PENDIENTE",
  "total": 90000,
  "direccionEnvio": "Calle Principal 123, Cali",
  "metodoPago": "TARJETA_CREDITO",
  "items": [
    {
      "producto": {
        "id": 1,
        "nombre": "Torta de Chocolate"
      },
      "cantidad": 2,
      "precioUnitario": 45000,
      "subtotal": 90000
    }
  ],
  "fechaCreacion": "2025-11-23T14:30:00"
}
```

### 7️⃣ Ver Mis Pedidos

**Pasos:**
1. Busca `GET /api/pedidos/usuario/{usuarioId}`
2. Click en **"Try it out"**
3. Ingresa tu `usuarioId`
4. Click en **"Execute"**

**Resultado:** Lista de todos tus pedidos.

### 8️⃣ Ver Detalle de un Pedido

**Pasos:**
1. Busca `GET /api/pedidos/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del pedido
4. Click en **"Execute"**

---

## 🔑 Endpoints de Admin (Requieren Rol ADMIN)

⚠️ **IMPORTANTE:** Debes estar autenticado como `admin@mrpastel.cl`

### 👥 GESTIÓN DE USUARIOS

#### 1️⃣ Listar Todos los Usuarios

**Pasos:**
1. Asegúrate de estar autenticado como ADMIN
2. Expande **"Usuarios"**
3. Busca `GET /api/usuarios`
4. Click en **"Try it out"**
5. Click en **"Execute"**

**Resultado (200):**
```json
[
  {
    "id": 1,
    "nombre": "Administrador",
    "email": "admin@mrpastel.cl",
    "rut": "11.111.111-1",
    "rol": "ADMIN",
    "fechaCreacion": "2025-11-01T10:00:00"
  },
  {
    "id": 2,
    "nombre": "Usuario Test",
    "email": "test@ejemplo.com",
    "rut": "22.222.222-2",
    "rol": "USUARIO",
    "fechaCreacion": "2025-11-23T12:00:00"
  }
]
```

#### 2️⃣ Ver Usuario por ID

**Pasos:**
1. Busca `GET /api/usuarios/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del usuario
4. Click en **"Execute"**

#### 3️⃣ Crear Nuevo Usuario

**Pasos:**
1. Busca `POST /api/usuarios`
2. Click en **"Try it out"**
3. Modifica el JSON:
   ```json
   {
     "nombre": "Nuevo Empleado",
     "email": "empleado@mrpastel.cl",
     "password": "empleado123",
     "rut": "33.333.333-3",
     "rol": "USUARIO"
   }
   ```
4. Click en **"Execute"**

**Roles válidos:**
- `ADMIN` - Acceso completo
- `USUARIO` - Acceso limitado

⚠️ **La contraseña será encriptada automáticamente con BCrypt**

#### 4️⃣ Actualizar Usuario

**Pasos:**
1. Busca `PUT /api/usuarios/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del usuario
4. Modifica los campos necesarios:
   ```json
   {
     "nombre": "Usuario Actualizado",
     "email": "actualizado@ejemplo.com",
     "rut": "33.333.333-3",
     "password": "newpassword123",
     "rol": "ADMIN"
   }
   ```
5. Click en **"Execute"**

💡 **Tip:** Si no quieres cambiar la contraseña, omite el campo `password`.

#### 5️⃣ Eliminar Usuario

**Pasos:**
1. Busca `DELETE /api/usuarios/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del usuario
4. Click en **"Execute"**

**Resultado:** Código 204 (No Content)

### 📦 GESTIÓN DE PRODUCTOS

#### 1️⃣ Crear Nuevo Producto

**Pasos:**
1. Expande **"Productos"**
2. Busca `POST /api/productos`
3. Click en **"Try it out"**
4. Modifica el JSON:
   ```json
   {
     "nombre": "Torta de Fresa",
     "descripcion": "Deliciosa torta de fresa con crema",
     "precio": 50000,
     "categoria": "Tortas",
     "stock": 15,
     "imagen": "/images/torta-fresa.jpg"
   }
   ```
5. Click en **"Execute"**

**Resultado (201):**
```json
{
  "id": 2,
  "nombre": "Torta de Fresa",
  "descripcion": "Deliciosa torta de fresa con crema",
  "precio": 50000,
  "categoria": "Tortas",
  "stock": 15,
  "imagen": "/images/torta-fresa.jpg",
  "fechaCreacion": "2025-11-23T15:00:00"
}
```

#### 2️⃣ Actualizar Producto

**Pasos:**
1. Busca `PUT /api/productos/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del producto
4. Modifica los campos:
   ```json
   {
     "nombre": "Torta de Chocolate Premium",
     "descripcion": "Torta de chocolate con ganache premium",
     "precio": 60000,
     "categoria": "Tortas",
     "stock": 8,
     "imagen": "/images/torta-chocolate-premium.jpg"
   }
   ```
5. Click en **"Execute"**

#### 3️⃣ Eliminar Producto

**Pasos:**
1. Busca `DELETE /api/productos/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del producto
4. Click en **"Execute"**

**Resultado:** Código 204 (No Content)

### 📋 GESTIÓN DE PEDIDOS

#### 1️⃣ Ver Todos los Pedidos

**Pasos:**
1. Expande **"Pedidos"**
2. Busca `GET /api/pedidos`
3. Click en **"Try it out"**
4. Click en **"Execute"**

**Resultado:** Lista de todos los pedidos del sistema.

#### 2️⃣ Actualizar Estado de Pedido

**Pasos:**
1. Busca `PUT /api/pedidos/{id}/estado`
2. Click en **"Try it out"**
3. Ingresa el ID del pedido
4. Selecciona el nuevo estado del dropdown:
   - `PENDIENTE` - Pedido recibido
   - `CONFIRMADO` - Pedido confirmado
   - `EN_PREPARACION` - En preparación
   - `ENVIADO` - Enviado al cliente
   - `ENTREGADO` - Entregado exitosamente
   - `CANCELADO` - Pedido cancelado
5. Click en **"Execute"**

**Ejemplo:**
```
Pedido ID: 1
Nuevo Estado: ENVIADO
```

**Resultado (200):**
```json
{
  "id": 1,
  "estado": "ENVIADO",
  ...
}
```

#### 3️⃣ Eliminar Pedido

**Pasos:**
1. Busca `DELETE /api/pedidos/{id}`
2. Click en **"Try it out"**
3. Ingresa el ID del pedido
4. Click en **"Execute"**

---

## 🐛 Solución de Problemas

### Error 401 (Unauthorized)

**Síntomas:**
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido o expirado"
}
```

**Soluciones:**
1. ✅ Verifica que copiaste el token completo
2. ✅ Asegúrate de incluir `Bearer ` antes del token
3. ✅ Verifica que el token no haya expirado (duran 24 horas)
4. ✅ Haz login nuevamente para obtener un nuevo token
5. ✅ Click en "Authorize" y configura el token de nuevo

### Error 403 (Forbidden)

**Síntomas:**
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "Acceso denegado"
}
```

**Causas:**
- Intentando acceder a un endpoint de ADMIN sin serlo
- Token válido pero sin permisos suficientes

**Solución:**
1. Verifica tu rol en la respuesta del login
2. Para endpoints de admin, usa: `admin@mrpastel.cl` / `admin123`

### Error 404 (Not Found)

**Síntomas:**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Recurso no encontrado"
}
```

**Causas:**
- ID no existe en la base de datos
- URL incorrecta

**Solución:**
1. Verifica que el ID existe (usa el GET para listar todos)
2. Usa IDs válidos de recursos existentes

### Error 400 (Bad Request)

**Síntomas:**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Datos inválidos"
}
```

**Causas:**
- JSON mal formado
- Campos requeridos faltantes
- Formato de datos incorrecto

**Solución:**
1. Revisa que el JSON esté bien formado (llaves, comas, comillas)
2. Verifica que todos los campos requeridos estén presentes
3. Valida formatos (email válido, números positivos, etc.)

### El Backend No Responde

**Síntomas:**
- Swagger no carga
- Endpoints no responden

**Soluciones:**
1. ✅ Verifica que el backend esté corriendo (revisa la consola)
2. ✅ Verifica que MySQL esté corriendo
3. ✅ Verifica que la base de datos `mr_pastel` exista
4. ✅ Revisa el puerto 8080 no esté ocupado
5. ✅ Reinicia el backend: `Ctrl+C` y luego `mvn spring-boot:run`

### Token Expirado

Los tokens JWT duran **24 horas**.

**Solución:**
1. Haz login nuevamente
2. Obtén un nuevo token
3. Actualiza el token en "Authorize"

---

## 📝 Resumen de Credenciales

### Usuario Admin
```
Email: admin@mrpastel.cl
Password: admin123
Rol: ADMIN
```

### Usuario de Prueba
Créalo con `POST /api/auth/registro` o usa el que creaste en la guía.

---

## ✅ Checklist de Pruebas Completas

### Endpoints Públicos
- [ ] GET Todos los productos
- [ ] GET Producto por ID
- [ ] GET Productos por categoría
- [ ] GET Buscar productos
- [ ] POST Registro de usuario
- [ ] POST Login
- [ ] POST Enviar contacto

### Endpoints de Usuario (Token requerido)
- [ ] GET Mi carrito
- [ ] POST Agregar al carrito
- [ ] PUT Actualizar cantidad
- [ ] DELETE Eliminar item
- [ ] DELETE Vaciar carrito
- [ ] POST Crear pedido
- [ ] GET Mis pedidos
- [ ] GET Detalle de pedido

### Endpoints de Admin (Token ADMIN requerido)
- [ ] GET Todos los usuarios
- [ ] GET Usuario por ID
- [ ] POST Crear usuario
- [ ] PUT Actualizar usuario
- [ ] DELETE Eliminar usuario
- [ ] POST Crear producto
- [ ] PUT Actualizar producto
- [ ] DELETE Eliminar producto
- [ ] GET Todos los pedidos
- [ ] PUT Actualizar estado pedido
- [ ] DELETE Eliminar pedido

---

## 🎯 Conclusión

¡Felicidades! Ahora sabes cómo probar todos los endpoints de la API Mr. Pastel usando Swagger.

**Recuerda:**
1. Swagger es una herramienta interactiva para probar APIs
2. Los tokens expiran en 24 horas
3. Algunos endpoints requieren rol ADMIN
4. Siempre verifica los códigos de respuesta

Para más información, consulta `API_DOCUMENTATION.md`.

---

**¿Necesitas ayuda?** Revisa la sección de [Solución de Problemas](#solución-de-problemas).

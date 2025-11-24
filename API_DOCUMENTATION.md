# API REST Mr. Pastel - Documentación Completa

**Versión**: 3.0.0  
**Última Actualización**: Noviembre 23, 2025  
**Estado**: ✅ Producción Ready

## 📋 Índice
1. [Base URL y Swagger](#base-url)
2. [⭐ Guía Paso a Paso para Probar en Swagger](GUIA_SWAGGER.md) ← **COMIENZA AQUÍ**
3. [Endpoints Disponibles](#endpoints-disponibles)
4. [Autenticación](#autenticación)
5. [Usuarios](#usuarios)
6. [Productos](#productos)
7. [Pedidos](#pedidos)
8. [Carrito](#carrito)
9. [Contacto](#contacto)
10. [Códigos de Error](#códigos-de-error)

---

## 🚀 ¿Cómo Probar la API?

📖 **[Ver Guía Completa Paso a Paso (GUIA_SWAGGER.md)](GUIA_SWAGGER.md)**

Esta guía incluye:
- ✅ Cómo iniciar el backend
- ✅ Cómo acceder a Swagger
- ✅ Cómo probar CADA endpoint con ejemplos
- ✅ Cómo autenticarse y usar tokens
- ✅ Solución de problemas comunes

**Credenciales de Admin para pruebas:**
```
Email: admin@mrpastel.cl
Password: admin123
```

---

## Base URL
```
http://localhost:8080/api
```

## Swagger UI (Documentación Interactiva)
```
http://localhost:8080/swagger-ui.html
```

## Endpoints Disponibles

| Categoría | Endpoints | Acceso |
|-----------|-----------|--------|
| **Autenticación** | 2 | Público |
| **Usuarios** | 5 | ADMIN |
| **Productos** | 8 | GET público, CRUD admin |
| **Pedidos** | 6 | Usuario propios, ADMIN todos |
| **Carrito** | 5 | Autenticado |
| **Contacto** | 1 | Público |
| **Total** | **27** | - |

---

## 🚀 Guía Paso a Paso para Probar en Swagger

### ✅ Paso 1: Iniciar el Backend

1. Abre una terminal en la carpeta `backend`
2. Ejecuta el backend:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
3. Espera a ver el mensaje: `Started BackendMrPastelApplication`
4. Verifica que esté corriendo en: `http://localhost:8080`

### ✅ Paso 2: Abrir Swagger UI

1. Abre tu navegador
2. Ve a: **http://localhost:8080/swagger-ui.html**
3. Verás la interfaz de Swagger con todas las categorías de endpoints

### ✅ Paso 3: Probar Endpoints Públicos (Sin Autenticación)

#### 3.1 Obtener Todos los Productos

1. **Expande** la sección: `Productos` (haz clic en la flecha)
2. **Busca** el endpoint: `GET /api/productos`
3. **Haz clic** en el botón azul **"Try it out"**
4. **Haz clic** en **"Execute"**
5. **Verás la respuesta** con la lista de productos en formato JSON

**Ejemplo de respuesta exitosa (200):**
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

#### 3.2 Obtener un Producto por ID

1. **Busca** el endpoint: `GET /api/productos/{id}`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** el ID del producto (ejemplo: `1`)
4. **Haz clic** en **"Execute"**
5. **Verás** los detalles del producto específico

#### 3.3 Registrar un Nuevo Usuario

1. **Expande** la sección: `Autenticación`
2. **Busca** el endpoint: `POST /api/auth/registro`
3. **Haz clic** en **"Try it out"**
4. **Modifica** el JSON con tus datos:
   ```json
   {
     "nombre": "Test Usuario",
     "email": "test@ejemplo.com",
     "password": "test123",
     "rut": "12.345.678-9"
   }
   ```
5. **Haz clic** en **"Execute"**
6. **Guarda el token** de la respuesta (lo necesitarás después)

**Ejemplo de respuesta (201):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0...",
  "id": 2,
  "email": "test@ejemplo.com",
  "nombre": "Test Usuario",
  "rut": "12.345.678-9",
  "rol": "USUARIO"
}
```

### ✅ Paso 4: Autenticarse para Endpoints Protegidos

#### 4.1 Hacer Login

1. **Busca** el endpoint: `POST /api/auth/login`
2. **Haz clic** en **"Try it out"**
3. **Usa las credenciales del admin** (o el usuario que creaste):
   ```json
   {
     "email": "admin@mrpastel.cl",
     "password": "admin123"
   }
   ```
4. **Haz clic** en **"Execute"**
5. **Copia el token** de la respuesta (ejemplo: `eyJhbGciOiJIUzUxMiJ9...`)

#### 4.2 Configurar el Token en Swagger

1. **En la parte superior derecha** de Swagger, busca el botón **"Authorize" 🔓**
2. **Haz clic** en **"Authorize"**
3. **En el campo "Value"** ingresa: `Bearer TU_TOKEN_AQUÍ`
   - Ejemplo: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1...`
   - ⚠️ **IMPORTANTE:** No olvides poner `Bearer ` antes del token
4. **Haz clic** en **"Authorize"**
5. **Haz clic** en **"Close"**
6. **Verás un candado cerrado 🔒** indicando que estás autenticado

### ✅ Paso 5: Probar Endpoints de Usuario (Requiere Token)

#### 5.1 Obtener Tu Carrito

1. **Expande** la sección: `Carrito`
2. **Busca** el endpoint: `GET /api/carrito/{usuarioId}`
3. **Haz clic** en **"Try it out"**
4. **Ingresa** el `usuarioId` (el ID que obtuviste al hacer login, ejemplo: `1`)
5. **Haz clic** en **"Execute"**
6. **Verás** tu carrito (puede estar vacío si es la primera vez)

**Respuesta esperada (200):**
```json
{
  "id": 1,
  "items": [],
  "total": 0
}
```

#### 5.2 Agregar Producto al Carrito

1. **Busca** el endpoint: `POST /api/carrito/{usuarioId}/agregar`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** los parámetros:
   - `usuarioId`: Tu ID de usuario (ejemplo: `1`)
   - `productoId`: ID del producto (ejemplo: `1`)
   - `cantidad`: Cantidad a agregar (ejemplo: `2`)
4. **Haz clic** en **"Execute"**
5. **Verás** el carrito actualizado con el producto

#### 5.3 Crear un Pedido

1. **Expande** la sección: `Pedidos`
2. **Busca** el endpoint: `POST /api/pedidos`
3. **Haz clic** en **"Try it out"**
4. **Modifica** el JSON:
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
5. **Haz clic** en **"Execute"**
6. **Verás** el pedido creado con estado `PENDIENTE`

#### 5.4 Obtener Tus Pedidos

1. **Busca** el endpoint: `GET /api/pedidos/usuario/{usuarioId}`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** tu `usuarioId`
4. **Haz clic** en **"Execute"**
5. **Verás** la lista de todos tus pedidos

### ✅ Paso 6: Probar Endpoints de Admin (Requiere Rol ADMIN)

⚠️ **Importante:** Debes estar autenticado como `admin@mrpastel.cl` con password `admin123`

#### 6.1 Crear un Nuevo Producto

1. **Expande** la sección: `Productos`
2. **Busca** el endpoint: `POST /api/productos`
3. **Haz clic** en **"Try it out"**
4. **Modifica** el JSON:
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
5. **Haz clic** en **"Execute"**
6. **Verás** el producto creado con su ID

#### 6.2 Actualizar un Producto

1. **Busca** el endpoint: `PUT /api/productos/{id}`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** el ID del producto a actualizar (ejemplo: `1`)
4. **Modifica** los campos que desees actualizar:
   ```json
   {
     "nombre": "Torta de Chocolate Premium",
     "descripcion": "Torta de chocolate premium con ganache",
     "precio": 55000,
     "categoria": "Tortas",
     "stock": 8,
     "imagen": "/images/torta-chocolate-premium.jpg"
   }
   ```
5. **Haz clic** en **"Execute"**
6. **Verás** el producto actualizado

#### 6.3 Eliminar un Producto

1. **Busca** el endpoint: `DELETE /api/productos/{id}`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** el ID del producto a eliminar
4. **Haz clic** en **"Execute"**
5. **Verás** respuesta `204 No Content` si fue exitoso

#### 6.4 Listar Todos los Usuarios

1. **Expande** la sección: `Usuarios`
2. **Busca** el endpoint: `GET /api/usuarios`
3. **Haz clic** en **"Try it out"**
4. **Haz clic** en **"Execute"**
5. **Verás** la lista completa de usuarios

#### 6.5 Crear un Nuevo Usuario (Admin)

1. **Busca** el endpoint: `POST /api/usuarios`
2. **Haz clic** en **"Try it out"**
3. **Modifica** el JSON:
   ```json
   {
     "nombre": "Nuevo Usuario",
     "email": "nuevo@ejemplo.com",
     "password": "password123",
     "rut": "98.765.432-1",
     "rol": "USUARIO"
   }
   ```
4. **Haz clic** en **"Execute"**
5. **Verás** el usuario creado

#### 6.6 Actualizar Estado de un Pedido

1. **Expande** la sección: `Pedidos`
2. **Busca** el endpoint: `PUT /api/pedidos/{id}/estado`
3. **Haz clic** en **"Try it out"**
4. **Ingresa** el ID del pedido
5. **Selecciona** el nuevo estado del dropdown:
   - `PENDIENTE`
   - `CONFIRMADO`
   - `EN_PREPARACION`
   - `ENVIADO`
   - `ENTREGADO`
   - `CANCELADO`
6. **Haz clic** en **"Execute"**
7. **Verás** el pedido actualizado

### ✅ Paso 7: Probar Otros Endpoints

#### 7.1 Buscar Productos por Nombre

1. **Busca** el endpoint: `GET /api/productos/buscar`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** un término de búsqueda (ejemplo: `chocolate`)
4. **Haz clic** en **"Execute"**
5. **Verás** productos que coincidan con la búsqueda

#### 7.2 Obtener Productos por Categoría

1. **Busca** el endpoint: `GET /api/productos/categoria/{categoria}`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** una categoría (ejemplo: `Tortas`)
4. **Haz clic** en **"Execute"**
5. **Verás** solo productos de esa categoría

#### 7.3 Enviar Mensaje de Contacto

1. **Expande** la sección: `Contacto`
2. **Busca** el endpoint: `POST /api/contacto`
3. **Haz clic** en **"Try it out"**
4. **Modifica** el JSON:
   ```json
   {
     "nombre": "Juan Pérez",
     "email": "juan@ejemplo.com",
     "asunto": "Consulta sobre productos",
     "mensaje": "Quisiera saber más sobre las tortas sin azúcar"
   }
   ```
5. **Haz clic** en **"Execute"**
6. **Verás** confirmación del mensaje enviado

#### 7.4 Vaciar el Carrito

1. **Busca** el endpoint: `DELETE /api/carrito/{usuarioId}/vaciar`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** tu `usuarioId`
4. **Haz clic** en **"Execute"**
5. **Verás** respuesta `204 No Content`

#### 7.5 Eliminar un Item del Carrito

1. **Busca** el endpoint: `DELETE /api/carrito/{usuarioId}/eliminar-item/{itemId}`
2. **Haz clic** en **"Try it out"**
3. **Ingresa** `usuarioId` e `itemId`
4. **Haz clic** en **"Execute"**
5. **Verás** el carrito actualizado sin ese item

---

## 📌 Tips para Probar en Swagger

### ✅ Tips Generales

1. **Orden recomendado:**
   - Primero endpoints públicos (productos, registro)
   - Luego hacer login y obtener token
   - Configurar autorización en Swagger
   - Probar endpoints protegidos

2. **Si obtienes error 401 (Unauthorized):**
   - Verifica que copiaste el token completo
   - Asegúrate de incluir `Bearer ` antes del token
   - Verifica que el token no haya expirado (24 horas)
   - Haz login nuevamente si es necesario

3. **Si obtienes error 403 (Forbidden):**
   - Verifica que tu usuario tenga el rol adecuado
   - Usa `admin@mrpastel.cl` para endpoints de admin

4. **Si obtienes error 404 (Not Found):**
   - Verifica que el ID existe en la base de datos
   - Primero lista los recursos disponibles (GET all)

5. **Para ver respuestas completas:**
   - Expande la sección "Responses" en cada endpoint
   - Swagger muestra códigos de estado y ejemplos

### ✅ Credenciales de Prueba

**Admin:**
- Email: `admin@mrpastel.cl`
- Password: `admin123`
- Rol: `ADMIN`

**Usuario Normal:**
- Crea uno con `POST /api/auth/registro`
- O usa el que creaste en el paso 3.3

### ✅ Validar Datos

Todos los endpoints validan:
- ✅ Formato de email
- ✅ Campos requeridos
- ✅ Tipos de datos correctos
- ✅ Longitud de strings
- ✅ Valores numéricos positivos

---

## 1. AUTENTICACIÓN 🔐

### 1.1 Login
**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "admin@mrpastel.cl",
  "password": "admin123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "id": 1,
  "email": "admin@mrpastel.cl",
  "nombre": "Administrador",
  "rut": "11.111.111-1",
  "rol": "ADMIN"
}
```

**Nota**: El frontend debe mapear `rol` a `role` y `ADMIN`→`admin`, `USUARIO`→`user`

**Uso en Frontend React:**
```javascript
const login = async (email, password) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  const data = await response.json();
  
  // Mapear campos backend→frontend
  const user = {
    id: data.id,
    email: data.email,
    name: data.nombre,
    rut: data.rut,
    role: (data.rol === 'ADMIN' || data.role === 'admin') ? 'admin' : 'user'
  };
  
  localStorage.setItem('token', data.token);
  localStorage.setItem('user', JSON.stringify(user));
  return { ...user, token: data.token };
};
```

### 1.2 Registro
**Endpoint:** `POST /auth/registro`

**Request Body:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "password": "password123"
}
```

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "juan@example.com",
  "nombre": "Juan Pérez",
  "rol": "USUARIO"
}
```

---

## 2. PRODUCTOS (CRUD) 📦

### 2.1 Obtener todos los productos
**Endpoint:** `GET /productos`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Torta de Chocolate",
    "descripcion": "Deliciosa torta de chocolate con ganache",
    "precio": 45.00,
    "categoria": "Tortas",
    "stock": 10,
    "imagen": "https://example.com/imagen.jpg",
    "region": "CALI",
    "fechaCreacion": "2025-11-13T10:30:00"
  }
]
```

**Uso en Frontend:**
```javascript
const getProductos = async () => {
  const response = await fetch('http://localhost:8080/api/productos');
  return await response.json();
};
```

### 2.2 Obtener producto por ID
**Endpoint:** `GET /productos/{id}`

**Response (200 OK):**
```json
{
  "id": 1,
  "nombre": "Torta de Chocolate",
  "descripcion": "Deliciosa torta de chocolate con ganache",
  "precio": 45.00,
  "categoria": "Tortas",
  "stock": 10,
  "imagen": "https://example.com/imagen.jpg",
  "region": "CALI",
  "fechaCreacion": "2025-11-13T10:30:00"
}
```

### 2.3 Obtener productos por categoría
**Endpoint:** `GET /productos/categoria/{categoria}`

**Response (200 OK):**
```json
[
  { /* producto 1 */ },
  { /* producto 2 */ }
]
```

### 2.4 Obtener productos por región
**Endpoint:** `GET /productos/region/{region}`

**Response (200 OK):**
```json
[
  { /* producto */ }
]
```

### 2.5 Buscar productos por nombre
**Endpoint:** `GET /productos/buscar?nombre=chocolate`

**Response (200 OK):**
```json
[
  { /* productos que coincidan */ }
]
```

**Uso en Frontend:**
```javascript
const buscarProductos = async (nombre) => {
  const response = await fetch(`http://localhost:8080/api/productos/buscar?nombre=${nombre}`);
  return await response.json();
};
```

### 2.6 Crear producto (Admin)
**Endpoint:** `POST /productos`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Torta de Fresa",
  "descripcion": "Torta fresca con fresas naturales",
  "precio": 50.00,
  "categoria": "Tortas",
  "stock": 15,
  "imagen": "https://example.com/fresa.jpg",
  "region": "CALI"
}
```

**Response (201 Created):**
```json
{
  "id": 2,
  "nombre": "Torta de Fresa",
  "descripcion": "Torta fresca con fresas naturales",
  "precio": 50.00,
  "categoria": "Tortas",
  "stock": 15,
  "imagen": "https://example.com/fresa.jpg",
  "region": "CALI",
  "fechaCreacion": "2025-11-13T11:00:00"
}
```

### 2.7 Actualizar producto (Admin)
**Endpoint:** `PUT /productos/{id}`

**Headers:**
```
Authorization: Bearer {token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "nombre": "Torta de Fresa Premium",
  "descripcion": "Torta premium con fresas seleccionadas",
  "precio": 60.00,
  "categoria": "Tortas",
  "stock": 20,
  "imagen": "https://example.com/fresa-premium.jpg",
  "region": "CALI"
}
```

**Response (200 OK):**
```json
{
  "id": 2,
  "nombre": "Torta de Fresa Premium",
  ...
}
```

### 2.8 Eliminar producto (Admin)
**Endpoint:** `DELETE /productos/{id}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (204 No Content)**

---

## 3. CARRITO 🛒

### 3.1 Obtener carrito del usuario
**Endpoint:** `GET /carrito/{usuarioId}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "producto": {
        "id": 1,
        "nombre": "Torta de Chocolate",
        "precio": 45.00,
        ...
      },
      "cantidad": 2,
      "subtotal": 90.00
    }
  ],
  "total": 90.00
}
```

**Uso en Frontend:**
```javascript
const getCarrito = async (usuarioId, token) => {
  const response = await fetch(`http://localhost:8080/api/carrito/${usuarioId}`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return await response.json();
};
```

### 3.2 Agregar producto al carrito
**Endpoint:** `POST /carrito/{usuarioId}/agregar?productoId={id}&cantidad={cantidad}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "producto": { /* ... */ },
      "cantidad": 2,
      "subtotal": 90.00
    }
  ],
  "total": 90.00
}
```

**Uso en Frontend:**
```javascript
const agregarAlCarrito = async (usuarioId, productoId, cantidad, token) => {
  const response = await fetch(
    `http://localhost:8080/api/carrito/${usuarioId}/agregar?productoId=${productoId}&cantidad=${cantidad}`,
    {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` }
    }
  );
  return await response.json();
};
```

### 3.3 Actualizar cantidad de item
**Endpoint:** `PUT /carrito/{usuarioId}/actualizar-item/{itemId}?cantidad={cantidad}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{ /* carrito actualizado */ }
```

### 3.4 Eliminar item del carrito
**Endpoint:** `DELETE /carrito/{usuarioId}/eliminar-item/{itemId}`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{ /* carrito sin el item */ }
```

**Uso en Frontend:**
```javascript
const eliminarDelCarrito = async (usuarioId, itemId, token) => {
  const response = await fetch(
    `http://localhost:8080/api/carrito/${usuarioId}/eliminar-item/${itemId}`,
    {
      method: 'DELETE',
      headers: { 'Authorization': `Bearer ${token}` }
    }
  );
  return await response.json();
};
```

### 3.5 Vaciar carrito
**Endpoint:** `DELETE /carrito/{usuarioId}/vaciar`

**Headers:**
```
Authorization: Bearer {token}
```

**Response (204 No Content)**

---

## 4. CÓDIGOS DE ERROR 🚨

| Código | Significado |
|--------|-------------|
| 200 | OK - Solicitud exitosa |
| 201 | Created - Recurso creado |
| 204 | No Content - Sin contenido |
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - Token inválido o expirado |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

---

## 5. ESTRUCTURA DE TOKENS JWT

El token JWT contiene:
- **Header:** `{"alg": "HS512", "typ": "JWT"}`
- **Payload:**
  ```json
  {
    "sub": "usuario@example.com",
    "rol": "USUARIO",
    "iat": 1699861200000,
    "exp": 1699947600000
  }
  ```
- **Signature:** HMAC SHA-512

**Duración:** 24 horas (86400000 ms)

---

## 6. EJEMPLO COMPLETO EN REACT

```javascript
import React, { useContext } from 'react';

const API_BASE_URL = 'http://localhost:8080/api';

// Hook para obtener token
const useToken = () => localStorage.getItem('token');

// 1. Login
const handleLogin = async (email, password) => {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  const data = await response.json();
  localStorage.setItem('token', data.token);
  localStorage.setItem('usuarioId', data.id); // guardar ID
};

// 2. Obtener productos
const fetchProductos = async () => {
  const response = await fetch(`${API_BASE_URL}/productos`);
  return await response.json();
};

// 3. Agregar al carrito
const handleAddToCart = async (productoId, cantidad) => {
  const token = useToken();
  const usuarioId = localStorage.getItem('usuarioId');
  
  const response = await fetch(
    `${API_BASE_URL}/carrito/${usuarioId}/agregar?productoId=${productoId}&cantidad=${cantidad}`,
    {
      method: 'POST',
      headers: { 'Authorization': `Bearer ${token}` }
    }
  );
  return await response.json();
};

// 4. Obtener carrito
const fetchCarrito = async () => {
  const token = useToken();
  const usuarioId = localStorage.getItem('usuarioId');
  
  const response = await fetch(`${API_BASE_URL}/carrito/${usuarioId}`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  return await response.json();
};

export { handleLogin, fetchProductos, handleAddToCart, fetchCarrito };
```

---

## 7. CONFIGURACIÓN BD

**Base de Datos:** MySQL

**Crear BD:**
```sql
CREATE DATABASE mr_pastel;
```

**Tablas se crean automáticamente con:**
```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

---

## 8. INICIAR BACKEND

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

El servidor estará disponible en: `http://localhost:8080/api`

Swagger disponible en: `http://localhost:8080/swagger-ui.html`

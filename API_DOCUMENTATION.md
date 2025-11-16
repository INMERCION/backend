# API REST Mr. Pastel - Documentación Completa

**Versión**: 2.0.0  
**Última Actualización**: Noviembre 16, 2025  
**Estado**: ✅ Producción Ready

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
| **Total** | **26+** | - |

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

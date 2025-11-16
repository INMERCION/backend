-- Script para insertar usuarios de prueba con RUT
-- Nota: Las contraseñas están hasheadas con BCrypt

-- Limpiar tabla (opcional, descomentar si necesitas resetear)
DELETE FROM usuarios;

-- Insertar usuarios de prueba
-- IMPORTANTE: Estas contraseñas usan BCrypt con strength 10
-- Para generar nuevos hashes, usa: https://bcrypt-generator.com/

INSERT INTO usuarios (nombre, email, password, rut, rol, fecha_creacion) VALUES
-- Password: admin123
('Administrador', 'admin@mrpastel.cl', '$2a$10$Tmbr0mbRL7.x2hBnjVtrMOWSw02.wATk7Augjki06zbB1U4R4VyOC', '11.111.111-1', 'ADMIN', NOW()),

-- Password: user123
('Juan Pérez', 'jperez@gmail.com', '$2a$12$xT0EGIv3jtpc8QfKRaN9dOf0xKAp0TboD0VTZfqNeKGfG6Kg/5Bdu', '12.345.678-9', 'USUARIO', NOW()),

-- Password: user123
('María González', 'mgonzalez@outlook.com', '$2a$12$xT0EGIv3jtpc8QfKRaN9dOf0xKAp0TboD0VTZfqNeKGfG6Kg/5Bdu', '9.876.543-2', 'USUARIO', NOW()),

-- Password: user123
('Carlos Rodríguez', 'crodriguez@gmail.com', '$2a$12$xT0EGIv3jtpc8QfKRaN9dOf0xKAp0TboD0VTZfqNeKGfG6Kg/5Bdu', '15.432.765-K', 'USUARIO', NOW());

-- Verificar inserción
SELECT id, nombre, email, rut, rol FROM usuarios;

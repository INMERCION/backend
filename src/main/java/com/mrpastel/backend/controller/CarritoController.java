package com.mrpastel.backend.controller;

import com.mrpastel.backend.dto.CarritoDTO;
import com.mrpastel.backend.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Gestión del carrito de compras")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Obtener carrito del usuario")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@PathVariable Long usuarioId) {
        try {
            CarritoDTO carrito = carritoService.obtenerCarrito(usuarioId);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{usuarioId}/agregar")
    @Operation(summary = "Agregar producto al carrito")
    public ResponseEntity<CarritoDTO> agregarItem(
            @PathVariable Long usuarioId,
            @RequestParam Long productoId,
            @RequestParam(defaultValue = "1") Integer cantidad) {
        try {
            CarritoDTO carrito = carritoService.agregarItem(usuarioId, productoId, cantidad);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{usuarioId}/actualizar-item/{itemId}")
    @Operation(summary = "Actualizar cantidad de item en carrito")
    public ResponseEntity<CarritoDTO> actualizarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long itemId,
            @RequestParam Integer cantidad) {
        try {
            CarritoDTO carrito = carritoService.actualizarItem(usuarioId, itemId, cantidad);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{usuarioId}/eliminar-item/{itemId}")
    @Operation(summary = "Eliminar item del carrito")
    public ResponseEntity<CarritoDTO> eliminarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long itemId) {
        try {
            CarritoDTO carrito = carritoService.eliminarItem(usuarioId, itemId);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{usuarioId}/vaciar")
    @Operation(summary = "Vaciar carrito")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        try {
            carritoService.vaciarCarrito(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

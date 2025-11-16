package com.mrpastel.backend.controller;

import com.mrpastel.backend.dto.CrearPedidoRequest;
import com.mrpastel.backend.dto.PedidoDTO;
import com.mrpastel.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión de Pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido")
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody CrearPedidoRequest request) {
        try {
            PedidoDTO pedido = pedidoService.crearPedido(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos")
    public ResponseEntity<List<PedidoDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener pedidos de un usuario")
    public ResponseEntity<List<PedidoDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPorUsuario(usuarioId));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de un pedido")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            return ResponseEntity.ok(pedidoService.actualizarEstado(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar un pedido")
    public ResponseEntity<PedidoDTO> cancelar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pedidoService.cancelarPedido(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

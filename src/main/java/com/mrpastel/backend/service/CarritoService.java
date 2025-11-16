package com.mrpastel.backend.service;

import com.mrpastel.backend.dto.CarritoDTO;
import com.mrpastel.backend.dto.ItemCarritoDTO;
import com.mrpastel.backend.entity.Carrito;
import com.mrpastel.backend.entity.ItemCarrito;
import com.mrpastel.backend.entity.Producto;
import com.mrpastel.backend.entity.Usuario;
import com.mrpastel.backend.repository.CarritoRepository;
import com.mrpastel.backend.repository.ItemCarritoRepository;
import com.mrpastel.backend.repository.ProductoRepository;
import com.mrpastel.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    // Obtener carrito del usuario
    public CarritoDTO obtenerCarrito(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
            .orElseGet(() -> crearCarrito(usuarioId));
        return convertirADTO(carrito);
    }

    // Crear carrito para nuevo usuario
    private Carrito crearCarrito(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Carrito carrito = Carrito.builder()
            .usuario(usuario)
            .build();
        return carritoRepository.save(carrito);
    }

    // Agregar item al carrito
    public CarritoDTO agregarItem(Long usuarioId, Long productoId, Integer cantidad) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
            .orElseGet(() -> crearCarrito(usuarioId));

        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Buscar si el producto ya está en el carrito
        var itemExistente = itemCarritoRepository.findByCarritoIdAndProductoId(carrito.getId(), productoId);
        
        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
            itemCarritoRepository.save(item);
        } else {
            ItemCarrito nuevoItem = ItemCarrito.builder()
                .carrito(carrito)
                .producto(producto)
                .cantidad(cantidad)
                .build();
            itemCarritoRepository.save(nuevoItem);
        }

        carrito = carritoRepository.findById(carrito.getId()).orElse(carrito);
        return convertirADTO(carrito);
    }

    // Actualizar cantidad de item
    public CarritoDTO actualizarItem(Long usuarioId, Long itemId, Integer cantidad) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (cantidad <= 0) {
            itemCarritoRepository.deleteById(itemId);
        } else {
            item.setCantidad(cantidad);
            itemCarritoRepository.save(item);
        }

        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return convertirADTO(carrito);
    }

    // Eliminar item del carrito
    public CarritoDTO eliminarItem(Long usuarioId, Long itemId) {
        itemCarritoRepository.deleteById(itemId);

        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return convertirADTO(carrito);
    }

    // Vaciar carrito
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        carrito.getItems().forEach(item -> itemCarritoRepository.delete(item));
    }

    private CarritoDTO convertirADTO(Carrito carrito) {
        return CarritoDTO.builder()
            .id(carrito.getId())
            .items(carrito.getItems().stream()
                .map(item -> ItemCarritoDTO.builder()
                    .id(item.getId())
                    .producto(productoService.obtenerPorId(item.getProducto().getId()))
                    .cantidad(item.getCantidad())
                    .subtotal(item.getSubtotal())
                    .build())
                .collect(Collectors.toList()))
            .total(carrito.getTotal())
            .build();
    }
}

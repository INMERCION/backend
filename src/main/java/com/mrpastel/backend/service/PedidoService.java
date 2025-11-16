package com.mrpastel.backend.service;

import com.mrpastel.backend.dto.CrearPedidoRequest;
import com.mrpastel.backend.dto.PedidoDTO;
import com.mrpastel.backend.dto.PedidoItemDTO;
import com.mrpastel.backend.entity.Pedido;
import com.mrpastel.backend.entity.PedidoItem;
import com.mrpastel.backend.entity.Producto;
import com.mrpastel.backend.entity.Usuario;
import com.mrpastel.backend.repository.PedidoRepository;
import com.mrpastel.backend.repository.ProductoRepository;
import com.mrpastel.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public PedidoDTO crearPedido(CrearPedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = Pedido.builder()
            .usuario(usuario)
            .total(request.getTotal())
            .estado("procesando")
            .build();

        // Agregar items al pedido
        for (CrearPedidoRequest.ItemPedidoRequest itemRequest : request.getProductos()) {
            Producto producto = productoRepository.findById(itemRequest.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemRequest.getProductoId()));

            PedidoItem item = PedidoItem.builder()
                .producto(producto)
                .cantidad(itemRequest.getCantidad())
                .precio(itemRequest.getPrecio())
                .build();

            pedido.addItem(item);
        }

        Pedido guardado = pedidoRepository.save(pedido);
        return convertirADTO(guardado);
    }

    public List<PedidoDTO> obtenerPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<PedidoDTO> obtenerTodos() {
        return pedidoRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public PedidoDTO obtenerPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return convertirADTO(pedido);
    }

    @Transactional
    public PedidoDTO actualizarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(nuevoEstado);
        Pedido actualizado = pedidoRepository.save(pedido);
        return convertirADTO(actualizado);
    }

    @Transactional
    public PedidoDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!"procesando".equals(pedido.getEstado())) {
            throw new RuntimeException("Solo se pueden cancelar pedidos en estado 'procesando'");
        }

        pedido.setEstado("cancelado");
        Pedido actualizado = pedidoRepository.save(pedido);
        return convertirADTO(actualizado);
    }

    private PedidoDTO convertirADTO(Pedido pedido) {
        List<PedidoItemDTO> itemsDTO = pedido.getItems().stream()
            .map(item -> PedidoItemDTO.builder()
                .id(item.getId())
                .productoId(item.getProducto().getId())
                .nombre(item.getProducto().getNombre())
                .imagen(item.getProducto().getImagen())
                .cantidad(item.getCantidad())
                .precio(item.getPrecio())
                .build())
            .collect(Collectors.toList());

        return PedidoDTO.builder()
            .id(pedido.getId())
            .usuarioId(pedido.getUsuario().getId())
            .usuarioNombre(pedido.getUsuario().getNombre())
            .total(pedido.getTotal())
            .estado(pedido.getEstado())
            .fechaCreacion(pedido.getFechaCreacion())
            .productos(itemsDTO)
            .build();
    }
}

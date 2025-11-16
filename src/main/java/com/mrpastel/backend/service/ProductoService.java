package com.mrpastel.backend.service;

import com.mrpastel.backend.dto.ProductoDTO;
import com.mrpastel.backend.entity.Producto;
import com.mrpastel.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // READ - Obtener todos los productos
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll()
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // READ - Obtener producto por ID
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirADTO(producto);
    }

    // READ - Obtener por categoría
    public List<ProductoDTO> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // READ - Obtener por región
    public List<ProductoDTO> obtenerPorRegion(String region) {
        return productoRepository.findByRegion(region)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // READ - Buscar por nombre
    public List<ProductoDTO> buscar(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // CREATE - Crear nuevo producto
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = Producto.builder()
            .nombre(dto.getNombre())
            .descripcion(dto.getDescripcion())
            .precio(dto.getPrecio())
            .categoria(dto.getCategoria())
            .stock(dto.getStock())
            .imagen(dto.getImagen())
            .region(dto.getRegion())
            .build();

        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }

    // UPDATE - Actualizar producto
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setStock(dto.getStock());
        producto.setImagen(dto.getImagen());
        producto.setRegion(dto.getRegion());

        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }

    // DELETE - Eliminar producto
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    private ProductoDTO convertirADTO(Producto producto) {
        return ProductoDTO.builder()
            .id(producto.getId())
            .nombre(producto.getNombre())
            .descripcion(producto.getDescripcion())
            .precio(producto.getPrecio())
            .categoria(producto.getCategoria())
            .stock(producto.getStock())
            .imagen(producto.getImagen())
            .region(producto.getRegion())
            .fechaCreacion(producto.getFechaCreacion())
            .build();
    }
}

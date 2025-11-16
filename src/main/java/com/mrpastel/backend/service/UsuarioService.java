package com.mrpastel.backend.service;

import com.mrpastel.backend.dto.UsuarioDTO;
import com.mrpastel.backend.entity.Usuario;
import com.mrpastel.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (usuarioDTO.getRut() != null && usuarioRepository.existsByRut(usuarioDTO.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        Usuario usuario = Usuario.builder()
            .nombre(usuarioDTO.getNombre())
            .email(usuarioDTO.getEmail())
            .rut(usuarioDTO.getRut())
            .password(passwordEncoder.encode("password123")) // Password por defecto
            .rol(usuarioDTO.getRol() != null ? usuarioDTO.getRol() : "USUARIO")
            .build();

        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar email único
        if (!usuario.getEmail().equals(usuarioDTO.getEmail()) 
            && usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Verificar RUT único
        if (usuarioDTO.getRut() != null 
            && !usuarioDTO.getRut().equals(usuario.getRut())
            && usuarioRepository.existsByRut(usuarioDTO.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setRut(usuarioDTO.getRut());
        usuario.setRol(usuarioDTO.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        return UsuarioDTO.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .email(usuario.getEmail())
            .rut(usuario.getRut())
            .rol(usuario.getRol())
            .fechaCreacion(usuario.getFechaCreacion())
            .build();
    }
}

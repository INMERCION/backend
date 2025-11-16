package com.mrpastel.backend.service;

import com.mrpastel.backend.dto.LoginRequest;
import com.mrpastel.backend.dto.JwtResponse;
import com.mrpastel.backend.dto.RegistroRequest;
import com.mrpastel.backend.entity.Usuario;
import com.mrpastel.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getRol());
        return new JwtResponse(token, usuario.getId(), usuario.getEmail(), 
                               usuario.getNombre(), usuario.getRut(), usuario.getRol());
    }

    public JwtResponse registro(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (request.getRut() != null && usuarioRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        Usuario usuario = Usuario.builder()
            .nombre(request.getNombre())
            .email(request.getEmail())
            .rut(request.getRut())
            .password(passwordEncoder.encode(request.getPassword()))
            .rol("USUARIO")
            .build();

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario.getEmail(), usuario.getRol());
        return new JwtResponse(token, usuario.getId(), usuario.getEmail(), 
                               usuario.getNombre(), usuario.getRut(), usuario.getRol());
    }
}

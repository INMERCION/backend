package com.mrpastel.backend.controller;

import com.mrpastel.backend.dto.LoginRequest;
import com.mrpastel.backend.dto.JwtResponse;
import com.mrpastel.backend.dto.RegistroRequest;
import com.mrpastel.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación (Login y Registro)")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Retorna JWT si las credenciales son válidas")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            JwtResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/registro")
    @Operation(summary = "Registro de nuevo usuario", description = "Crea un nuevo usuario y retorna JWT")
    public ResponseEntity<JwtResponse> registro(@Valid @RequestBody RegistroRequest request) {
        try {
            JwtResponse response = authService.registro(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

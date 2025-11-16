package com.mrpastel.backend.config;

import com.mrpastel.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull; // Asegúrate de importar NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importa para roles
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections; // Importa para roles

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // --- INICIO DE LA CORRECCIÓN ---
        //
        // Si no hay token (es una petición pública como /registro)
        // o si no es un token "Bearer", deja pasar la petición
        // al siguiente filtro y NO HAGAS NADA MÁS.
        //
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // <-- ¡¡ESTA LÍNEA ARREGLA TU ERROR 403!!
        }
        // --- FIN DE LA CORRECCIÓN ---


        // Si el código llega aquí, SÍ hay un token para validar.
        final String token = authHeader.substring(7);
        final String email;

        try {
            if (jwtService.validateToken(token)) {
                email = jwtService.extractEmail(token);
                // Extrae el ROL del token (importante para permisos)
                String rol = jwtService.extractRole(token); 
                
                var authorities = Collections.singletonList(new SimpleGrantedAuthority(rol));
                
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
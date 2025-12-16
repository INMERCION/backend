package com.mrpastel.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                
                // --- ENDPOINTS PÚBLICOS ---

                // 1. Autenticación (Login y Registro)
                .requestMatchers("/api/auth/**").permitAll()
                
                // 2. Swagger UI - CRÍTICO: incluir /v3/api-docs sin /**
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/v3/api-docs",        // CRÍTICO: Sin este da 403
                    "/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                
                // 3. Productos - TODOS los endpoints GET son públicos
                .requestMatchers(HttpMethod.GET, "/api/productos", "/api/productos/**").permitAll()
                
                // 4. Contacto - público
                .requestMatchers(HttpMethod.POST, "/api/contacto").permitAll()
                
                // 5. Pagos con Stripe - públicos (necesario para webhooks y creación de intents)
                .requestMatchers("/api/pagos/**").permitAll()
                
                // 6. Usuarios - requiere autenticación (GET, POST, PUT, DELETE)
                .requestMatchers("/api/usuarios/**").authenticated()
                
                // 7. Pedidos - requiere autenticación
                .requestMatchers("/api/pedidos/**").authenticated()
                
                // --- ENDPOINTS PRIVADOS ---
                
                // 7. El resto requiere autenticación (carrito, etc.)
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir orígenes específicos con Elastic IP fija
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",      // React Create App
            "http://localhost:8080",      // Mismo servidor (Swagger)
            "http://localhost:5173",      // Vite
            "http://127.0.0.1:3000",
            "http://127.0.0.1:5500",      // Live Server VSCode
            "http://98.91.93.249:3000"    // Elastic IP fija de EC2
        ));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permitir credenciales (cookies, tokens)
        configuration.setAllowCredentials(true);
        
        // Headers expuestos al cliente
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Tiempo de cache para preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

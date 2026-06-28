package com.tranquilconnect.tranquilconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            
            // Desactivamos CSRF globalmente para evitar bloqueos en los retornos de pago
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/usuarios/perfil","/usuarios/perfil/guardar")
                .hasAnyRole("PSICOLOGO", "ADMIN")
                .requestMatchers("/dashboard/**", "/usuarios/**", "/gruposcrud/**").hasRole("ADMIN")
                .requestMatchers("/grupo/*/miembros", "/chat/**", "/grupos/**").permitAll()
                // Abrimos completamente las rutas de pago y notificaciones
                .requestMatchers("/notificaciones-mp", "/pago-exitoso", "/pago-fallido", "/pago-pendiente").permitAll()
                .requestMatchers("/", "/index", "/login", "/registro", "/recuperar-clave",
                        "/enviar-codigo", "/verificar-y-cambiar", "/registro-psicologo",
                        "/css/**", "/js/**", "/images/**", "/tranquil_connect_new_logo.png").permitAll()
                .requestMatchers("/agendar")
                .hasAnyRole("USUARIO","PSICOLOGO","ADMIN")
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
        "http://localhost:8000",
        "http://127.0.0.1:8000",
        "https://www.mercadopago.com",
        "https://departure-neatness-edge.ngrok-free.dev"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities().stream()
                    .map(a -> a.getAuthority()).toList();
            if (authorities.contains("ROLE_ADMIN")) response.sendRedirect("/dashboard");
            else if (authorities.contains("ROLE_PSICOLOGO")) response.sendRedirect("/perfil-psicologo");
            else response.sendRedirect("/");
        };
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
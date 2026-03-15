package com.tranquilconnect.tranquilconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. RESTRICCIONES DE ADMIN
                .requestMatchers("/dashboard/**", "/usuarios/**", "/gruposcrud/**").hasRole("ADMIN")
                
                // 2. RUTAS PÚBLICAS
                .requestMatchers(
                    "/", "/index", "/login", "/registro", "/recuperar-clave", 
                    "/enviar-codigo", "/verificar-y-cambiar", "/registro-psicologo", 
                    "/css/**", "/js/**", "/images/**",
                    "/tranquil_connect_new_logo.png"
                ).permitAll()
                
                // 3. CUALQUIER OTRA RUTA
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
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            
            var authorities = authentication.getAuthorities().stream()
                                            .map(a -> a.getAuthority())
                                            .toList();

            if (authorities.contains("ROLE_ADMIN")) {
                response.sendRedirect("/dashboard"); 
            } 
            else if (authorities.contains("ROLE_PSICOLOGO")) {
                response.sendRedirect("/perfil-psicologo"); 
            } 
            else {
                // ✅ CAMBIO: Redirigir a la raíz "/" (Index) en lugar de "/grupos"
                response.sendRedirect("/"); 
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
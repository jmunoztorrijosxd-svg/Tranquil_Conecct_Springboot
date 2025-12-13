package com.tranquilconnect.tranquilconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// Importación necesaria para manejar la redirección por roles
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// Importa tu Handler personalizado o el de Spring si lo tienes

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                
                // 1. RESTRICCIONES DE ADMIN
                // 🚨 Solo los usuarios con el rol 'ADMIN' pueden acceder a estas rutas.
                // Spring Security asume el prefijo ROLE_ si usas .hasRole()
                .requestMatchers("/dashboard/**", "/usuarios/**").hasRole("ADMIN")
                
                // 2. RUTAS PÚBLICAS
                // Rutas accesibles por todos (incluidos no autenticados)
                .requestMatchers(
                    "/", "/index", "/login", "/registro", 
                    "/css/**", "/js/**", "/images/**",
                    "/tranquil_connect_new_logo.png"
                ).permitAll()
                
                // 3. RUTAS DE USUARIO COMÚN (Si tienes una página de inicio de usuario)
                // Si /home es la página del usuario, solo requiere que esté autenticado
                // .requestMatchers("/home").authenticated() 
                
                // 4. CUALQUIER OTRA RUTA
                // Cualquier otra URL requiere que el usuario esté autenticado (incluye /home si no se especifica arriba)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                // 🚨 CONFIGURACIÓN DE REDIRECCIÓN CLAVE: Usamos un SuccessHandler 
                // para que el ADMIN vaya al dashboard y el USUARIO a una página de usuario.
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

    // 🚨 Bean para manejar la lógica de redirección por rol
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Verificar si el usuario autenticado tiene el rol ADMIN
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")); // Nota: Spring Security usa ROLE_

            if (isAdmin) {
                // Si es ADMIN, redirige al dashboard
                response.sendRedirect("/dashboard"); 
            } else {
                // Si es USUARIO (o cualquier otro rol), redirige a la página principal o a una página de usuario
                // Asumiendo que / es tu página de usuario, si tienes /home úsalo aquí
                response.sendRedirect("/"); 
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ⚠️ ADVERTENCIA: NoOpPasswordEncoder no es seguro para producción. 
        // Usa BCryptPasswordEncoder para encriptar contraseñas.
        return NoOpPasswordEncoder.getInstance();
    }
}
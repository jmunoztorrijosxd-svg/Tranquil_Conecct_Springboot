package com.tranquilconnect.tranquilconnect.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Nota: Ya no necesitas importar UsuarioRepository, PostMapping ni @RequestParam aquí.
// Spring Security gestiona el POST /login.

@Controller
public class LoginController {

    // 1. Muestra la página de login (GET)
    // Spring Security maneja la redirección a esta página si la autenticación falla.
    @GetMapping("/login")
    public String mostrarpagina() {
        return "login"; // Retorna la vista login.html
    }

    /*
     * 2. PROCESAMIENTO DEL LOGIN (MÉTODO POST)
     * * ELIMINADO: Spring Security toma control de la ruta POST /login 
     * automáticamente, utilizando el UserDetailsServiceImpl que creaste. 
     * No necesitas un método @PostMapping("/login") aquí.
     */
     
    /*
     * 3. Nuevo Método: Cierra la sesión (GET /logout)
     * * NOTA: Este método también lo gestiona Spring Security automáticamente 
     * gracias a la configuración en SecurityConfig.java.
     * Si no tienes lógica adicional, es mejor dejar que Spring Security lo maneje.
     */
    /*
    @GetMapping("/logout")
    public String logout() {
        // La configuración en SecurityConfig.java define que /logout redirija a /index
        return "redirect:/index";
    }
    */
}
package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario; 
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; // <-- NECESARIO
import org.springframework.security.core.Authentication; // <-- NECESARIO
import org.springframework.security.core.context.SecurityContextHolder; // <-- NECESARIO
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    // 1. Inyectar el repositorio para buscar el objeto Usuario
    private final UsuarioRepository usuarioRepository;

    public HomeController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping({"/", "/index"}) 
    public String inicio(Model model) {
        
        // --- LÓGICA CORREGIDA DE SPRING SECURITY ---

        // Obtener el objeto de autenticación del contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Verificar si el usuario está realmente autenticado (y no es anónimo)
        if (authentication != null && authentication.isAuthenticated() && 
            // Esto comprueba si el usuario no es la entidad 'anónima' predeterminada
            !"anonymousUser".equals(authentication.getPrincipal())) {
            
            // El 'name' de la autenticación es el "username" que usamos para loguear (el email)
            String userEmail = authentication.getName();
            
            // 3. Buscar el objeto Usuario completo en la BD usando el email
            Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(userEmail);

            if (usuarioOptional.isPresent()) {
                // 4. Si se encuentra, agregar el objeto 'usuario' al modelo
                model.addAttribute("usuario", usuarioOptional.get());
            }
        }
        
        return "index";
    }
}
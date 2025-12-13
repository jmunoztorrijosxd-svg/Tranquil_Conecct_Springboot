package com.tranquilconnect.tranquilconnect.controladores;

// Importaciones de Spring Security
import org.springframework.security.core.annotation.AuthenticationPrincipal; // ¡NUEVA IMPORTACIÓN!
import org.springframework.security.core.userdetails.UserDetails; // ¡NUEVA IMPORTACIÓN!
import org.springframework.beans.factory.annotation.Autowired; // Necesario para inyectar el repositorio

import com.tranquilconnect.tranquilconnect.model.Usuario; 
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; // ¡NUEVA IMPORTACIÓN!

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;

// Ya no necesitamos la importación de HttpSession si la eliminamos de los parámetros
// import jakarta.servlet.http.HttpSession; 

@Controller
public class ForosocialController {
    
    // Necesitamos el repositorio para buscar el objeto Usuario completo
    @Autowired
    private UsuarioRepository usuarioRepository; 

    // MÉTODO MODIFICADO: Inyectamos el usuario de Spring Security
    @GetMapping("/forosocial")
    public String mostrarpagina(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = null;
        
        if (userDetails != null) {
            // 1. OBTENER CORREO (Username) DE LA SESIÓN DE SPRING SECURITY
            String correo = userDetails.getUsername(); 
            
            // 2. BUSCAR EL OBJETO Usuario COMPLETO DE LA BD
            usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        }
        
        // 3. Pasar el usuario al modelo (disponible en Thymeleaf como ${usuario})
        model.addAttribute("usuario", usuario);
        
        return "forosocial";
    }
}
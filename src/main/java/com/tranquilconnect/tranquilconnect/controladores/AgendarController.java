package com.tranquilconnect.tranquilconnect.controladores;

// Importaciones de Spring Security
import org.springframework.security.core.annotation.AuthenticationPrincipal; // ¡NUEVA IMPORTACIÓN!
import org.springframework.security.core.userdetails.UserDetails; // ¡NUEVA IMPORTACIÓN!

import com.tranquilconnect.tranquilconnect.model.Usuario; 
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; // ¡NUEVA IMPORTACIÓN!
import com.tranquilconnect.tranquilconnect.model.Psicologo; 
import com.tranquilconnect.tranquilconnect.repository.PsicologoRepository; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List; 
// Ya no necesitamos la importación de HttpSession ni List

@Controller
public class AgendarController {

    @Autowired
    private PsicologoRepository psicologoRepository;

    // Se necesita el repositorio del usuario para obtener el objeto completo
    @Autowired 
    private UsuarioRepository usuarioRepository; 

    @GetMapping("/agendar")
    // MÉTODO MODIFICADO: Inyectamos el usuario de Spring Security
    public String mostrarpagina(Model model, @AuthenticationPrincipal UserDetails userDetails) { 
        
        Usuario usuario = null;
        
        if (userDetails != null) {
            // 1. OBTENER CORREO (Username) DE LA SESIÓN DE SPRING SECURITY
            String correo = userDetails.getUsername(); 
            
            // 2. BUSCAR EL OBJETO Usuario COMPLETO DE LA BD
            usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        }
        
        // 3. PASAR USUARIO AL MODELO (Puede ser null si el usuario no está logueado, 
        // pero la ruta debería estar protegida por Spring Security)
        model.addAttribute("usuario", usuario);
        
        // 4. Obtener la lista de psicólogos de la BD
        List<Psicologo> listaPsicologos = psicologoRepository.findAll();
        
        // 5. Añadir la lista de psicólogos al objeto Model
        model.addAttribute("psicologos", listaPsicologos);
        
        // 6. Devuelve el nombre de la plantilla
        return "agendar";
    }
}
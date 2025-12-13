package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; // ¡NUEVA IMPORTACIÓN!

// Importaciones de Spring Security
import org.springframework.security.core.annotation.AuthenticationPrincipal; // ¡NUEVA IMPORTACIÓN!
import org.springframework.security.core.userdetails.UserDetails; // ¡NUEVA IMPORTACIÓN!
import org.springframework.beans.factory.annotation.Autowired; // ¡NUEVA IMPORTACIÓN!

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Ya no necesitamos la importación de HttpSession si la eliminamos de los parámetros
// import jakarta.servlet.http.HttpSession; 

@Controller
public class ChatController {

    // Necesitamos el repositorio para buscar el objeto Usuario completo
    @Autowired
    private UsuarioRepository usuarioRepository; 

    @GetMapping("/chat")
    public String mostrarChat(
        @RequestParam(name = "id", required = false) Long id, 
        Model model, 
        // CAMBIO CLAVE: Inyectamos el usuario autenticado de Spring Security
        @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = null;
        
        // La validación de login la hace Spring Security (en SecurityConfig). 
        // Aquí solo verificamos que tengamos los detalles.
        if (userDetails != null) {
            // 1. Obtener el correo (que es el username)
            String correo = userDetails.getUsername(); 
            
            // 2. Buscar el objeto Usuario completo en la BD
            usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        }

        // Si Spring Security está configurado correctamente, esta verificación
        // ya no es necesaria si la ruta está protegida, pero es una buena práctica:
        if (usuario == null) {
            // Si el usuario no fue encontrado en la BD (caso raro), redirigimos al login
            return "redirect:/login"; 
        }

        // 3. Lógica de UI (Necesaria para el navbar)
        model.addAttribute("usuario", usuario);

        // 4. Lógica de Grupos (Simulación de datos del grupo)
        String nombreGrupo = "Grupo Desconocido";
        String idGrupoDisplay = id != null ? id.toString() : "N/A";

        if (id != null) {
            if (id == 1) {
                nombreGrupo = "Superando la Ansiedad";
            } else if (id == 2) {
                nombreGrupo = "Técnicas de Mindfulness";
            } else {
                nombreGrupo = "Grupo ID: " + id;
            }
        }
        
        // 5. Pasar los datos a la vista
        model.addAttribute("idGrupo", idGrupoDisplay);
        model.addAttribute("nombreGrupo", nombreGrupo);

        return "chat";
    }
}
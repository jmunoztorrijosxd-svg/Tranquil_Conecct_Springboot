package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class DashboardController { // Asumo que el nombre es este

    @Autowired
    private UsuarioRepository usuarioRepository; // Necesitas el repositorio para obtener el usuario autenticado

    // Método Auxiliar para obtener el Usuario de la Sesión
    private Optional<Usuario> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String correo = authentication.getName();
            // Asumo que tienes un método findByCorreo en tu UsuarioRepository
            return usuarioRepository.findByCorreo(correo); 
        }
        return Optional.empty();
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
        return "dashboard"; // Retorna templates/dashboard.html
    }
    
    // NOTA: EL RESTO DE LAS RUTAS DE DASHBOARD (CITAS, FOROS) LAS PUEDES DEJAR AQUÍ O EN OTROS CONTROLADORES.
    // LA GESTIÓN DE USUARIOS LA MOVEREMOS AL USUARIOCONTROLLER.
}
package com.tranquilconnect.tranquilconnect.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // ¡NUEVA IMPORTACIÓN!
import org.springframework.security.core.userdetails.UserDetails; // ¡NUEVA IMPORTACIÓN!

import com.tranquilconnect.tranquilconnect.model.Grupo;
import com.tranquilconnect.tranquilconnect.repository.GrupoRepository;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; // Si necesitas el repositorio

@Controller
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired // Necesitarás el repositorio para obtener el objeto Usuario completo
    private UsuarioRepository usuarioRepository; 

    @GetMapping("/grupos")
    // MODIFICADO: Inyectamos el usuario de Spring Security directamente
    public String mostrarPaginaGrupos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        
        if (userDetails != null) {
            // 1. OBTENER EL CORREO (Username) DE LA SESIÓN DE SPRING SECURITY
            String correo = userDetails.getUsername(); 
            
            // 2. BUSCAR EL OBJETO Usuario COMPLETO DE LA BD
            // Spring Security solo nos da el email y la contraseña. Buscamos el objeto de nuestro modelo.
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null); 
            
            // 3. PASAR USUARIO AL MODELO
            model.addAttribute("usuario", usuario); 
        }
        
        // Obtener grupos desde la BD
        List<Grupo> lista = grupoRepository.findAll();

        // Enviarlos a la vista
        model.addAttribute("listaDeGrupos", lista);

        // Retorna la vista grupos.html
        return "grupos";
    }
}
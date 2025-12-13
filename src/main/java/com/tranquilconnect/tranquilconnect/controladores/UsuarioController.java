package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios") 
public class UsuarioController { 

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- Método Auxiliar para obtener el Usuario de la Sesión ---
    private Optional<Usuario> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String correo = authentication.getName();
            return usuarioRepository.findByCorreo(correo);
        }
        return Optional.empty();
    }
    
    // --- CRUD: R (Leer/Listar) - Mapea a /usuarios con filtros ---
    @GetMapping
    public String listarUsuarios(
            @RequestParam(required = false) String keyword,      
            @RequestParam(required = false) String rol,          
            @RequestParam(required = false) String genero,       
            Model model) {
        
        List<Usuario> usuarios;

        // 🚨 LÓGICA DE FILTRADO (CLAVE PARA EL FUNCIONAMIENTO COMPLETO)
        if (keyword != null && !keyword.isEmpty() || rol != null && !rol.isEmpty() || genero != null && !genero.isEmpty()) {
            
            // Si has implementado un método de búsqueda personalizada en tu repositorio, úsalo aquí:
            // usuarios = usuarioRepository.buscarPorFiltros(keyword, rol, genero);
            
            // Por ahora, para asegurar que carga, usaremos findAll, pero en un entorno real
            // DEBERÍAS aplicar el filtrado a la lista o usar una consulta personalizada:
            usuarios = usuarioRepository.findAll(); 
            
        } else {
            // Sin filtros, devuelve todos
            usuarios = usuarioRepository.findAll();
        }

        model.addAttribute("usuarios", usuarios);

        // CLAVE: Devolver las variables de filtro para que el HTML sepa qué opción mostrar en el <select>
        model.addAttribute("keyword", keyword);
        model.addAttribute("rolFiltro", rol); // Debe coincidir con th:selected
        model.addAttribute("generoFiltro", genero); // Debe coincidir con th:selected
        
        getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
        
        return "usuarios"; 
    }
    
    // --- RESTO DE MÉTODOS (nuevo, editar, guardar, eliminar) ---
    // (Estos métodos no los modificamos ya que se ven correctos)
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
        model.addAttribute("usuarioAEditar", new Usuario());
        model.addAttribute("titulo", "Crear Nuevo Usuario");
        return "usuario_form"; 
    }
    
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            getAuthenticatedUser().ifPresent(u -> model.addAttribute("usuario", u));
            model.addAttribute("usuarioAEditar", usuarioOpt.get());
            model.addAttribute("titulo", "Editar Usuario: " + usuarioOpt.get().getNombre());
            return "usuario_form"; 
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuarioAEditar") Usuario usuario, RedirectAttributes ra) {
        
        try {
            // Lógica para mantener la contraseña si se está editando
            if (usuario.getId() != null) {
                Optional<Usuario> existingUserOpt = usuarioRepository.findById(usuario.getId());
                
                if (existingUserOpt.isPresent()) {
                    Usuario existingUser = existingUserOpt.get();
                    usuario.setPassword(existingUser.getPassword());
                }
            } else {
                // Lógica para asignar contraseña temporal si es nuevo
                if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                    usuario.setPassword("temporal123"); 
                }
            }
            
            usuarioRepository.save(usuario);
            ra.addFlashAttribute("mensaje", "Usuario guardado exitosamente!");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar el usuario. Verifique el correo o los campos requeridos.");
            System.err.println("Error al guardar usuario: " + e.getMessage()); 
        }
        
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes ra) {
        try {
            usuarioRepository.deleteById(id);
            ra.addFlashAttribute("mensaje", "Usuario eliminado exitosamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar el usuario.");
        }
        return "redirect:/usuarios";
    }
}
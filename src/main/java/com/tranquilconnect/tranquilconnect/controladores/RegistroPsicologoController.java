package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // ⬅️ IMPORTANTE
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroPsicologoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Muestra el formulario de registro para psicólogos
    @GetMapping("/registro-psicologo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro-psicologo"; 
    }

    // 2. Procesa el envío del formulario
    @PostMapping("/registro-psicologo")
    public String registrarPsicologo(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            usuario.setRol("PSICOLOGO");
            usuario.setEstadoValidacion("PENDIENTE");
            usuarioRepository.save(usuario);
            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: Verifique los datos o el correo.");
            return "registro-psicologo";
        }
    }

    // 3. RUTA CORREGIDA: Controla si el psicólogo va a "Revisión" o al "Dashboard"
    @GetMapping("/perfil-psicologo")
    public String perfilPsicologo(Authentication authentication, Model model) {
        // Obtenemos el correo del usuario que acaba de iniciar sesión
        String correo = authentication.getName();
        
        // Buscamos sus datos actualizados en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 🚨 SI JULIAN YA LO APROBÓ: Mándalo a su dashboard real
        if ("APROBADO".equals(usuario.getEstadoValidacion())) {
            return "redirect:/dashboard-psicologo"; 
        }

        // SI SIGUE PENDIENTE: Muestra la vista de espera "perfil-en-revision"
        model.addAttribute("usuario", usuario);
        return "perfil-en-revision"; 
    }

    // 4. NUEVA RUTA: El destino final del psicólogo aprobado
    @GetMapping("/dashboard-psicologo")
    public String mostrarDashboardPsicologo(Authentication authentication, Model model) {
        String correo = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "dashboard-psicologo"; // Nombre del HTML para el panel del psicólogo
    }
}
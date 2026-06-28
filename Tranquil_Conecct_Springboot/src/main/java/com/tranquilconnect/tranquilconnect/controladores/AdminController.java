package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import com.tranquilconnect.tranquilconnect.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    // Listar solo psicólogos que esperan aprobación
    @GetMapping("/dashboard/solicitudes")
    public String listarSolicitudes(Model model) {
        List<Usuario> pendientes = usuarioRepository.findByRolAndEstadoValidacion(
                "PSICOLOGO",
                "PENDIENTE"
        );

        model.addAttribute("solicitudes", pendientes);

        return "admin/solicitudes-psicologos";
    }

    // Acción de aprobar al profesional
    @PostMapping("/admin/aprobar/{id}")
    public String aprobarPsicologo(@PathVariable Long id) {

        Usuario psicologo = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Psicólogo no encontrado"));

        // Aprobar psicólogo
        psicologo.setEstadoValidacion("APROBADO");
        usuarioRepository.save(psicologo);

        // Enviar correo de notificación
        try {
            emailService.enviarCorreo(
                    psicologo.getCorreo(),
                    "Perfil profesional aprobado - Tranquil Connect",
                    "Hola " + psicologo.getNombre() + ",\n\n" +
                    "Nos complace informarte que tu perfil profesional ha sido aprobado exitosamente por nuestro equipo de Tranquil Connect.\n\n" +
                    "A partir de este momento ya puedes iniciar sesión y comenzar a utilizar todas las funcionalidades disponibles para psicólogos dentro de la plataforma.\n\n" +
                    "¡Bienvenido a Tranquil Connect!\n\n" +
                    "Saludos cordiales,\n" +
                    "Equipo Tranquil Connect"
            );

            System.out.println("Correo enviado a: " + psicologo.getCorreo());

        } catch (Exception e) {

            System.err.println("Error al enviar correo: " + e.getMessage());

            // No detenemos la aprobación si falla el correo
        }

        return "redirect:/dashboard/solicitudes?exitoAprobacion";
    }
}
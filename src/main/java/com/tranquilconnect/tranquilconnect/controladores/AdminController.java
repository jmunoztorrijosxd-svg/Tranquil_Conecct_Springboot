package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
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

    // Listar solo psicólogos que esperan aprobación
    @GetMapping("/dashboard/solicitudes")
    public String listarSolicitudes(Model model) {
        List<Usuario> pendientes = usuarioRepository.findByRolAndEstadoValidacion("PSICOLOGO", "PENDIENTE");
        model.addAttribute("solicitudes", pendientes);
        return "admin/solicitudes-psicologos"; 
    }

    // Acción de aprobar al profesional
    @PostMapping("/admin/aprobar/{id}")
    public String aprobarPsicologo(@PathVariable Long id) {
        Usuario psicologo = usuarioRepository.findById(id).orElseThrow();
        psicologo.setEstadoValidacion("APROBADO");
        usuarioRepository.save(psicologo);
        return "redirect:/dashboard/solicitudes?exitoAprobacion";
    }
}
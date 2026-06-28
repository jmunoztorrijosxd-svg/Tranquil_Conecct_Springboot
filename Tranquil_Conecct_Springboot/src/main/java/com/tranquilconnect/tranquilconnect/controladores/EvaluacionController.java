package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Desempeno;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.CitaRepository;
import com.tranquilconnect.tranquilconnect.repository.DesempenoRepository;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class EvaluacionController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CitaRepository citaRepository;
    @Autowired private DesempenoRepository desempenoRepository;

    // MÉTODO PARA MOSTRAR EL FORMULARIO
    @GetMapping("/evaluar")
    public String mostrarFormulario(@RequestParam Long pacienteId, Model model) {
        Usuario paciente = usuarioRepository.findById(pacienteId).orElse(null);
        if (paciente == null) {
            return "redirect:/dashboard-psicologo"; 
        }
        model.addAttribute("paciente", paciente);
        return "evaluar"; 
    }

    // MÉTODO PARA GUARDAR LA EVALUACIÓN
    @PostMapping("/evaluacion/guardar")
    public String guardarEvaluacion(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam Long pacienteId, 
                                     @RequestParam Integer calificacion, 
                                     @RequestParam(required = false) String descripcion, 
                                     @RequestParam(required = false) String condicion,
                                     RedirectAttributes redirectAttributes) {
        
        Usuario psicologo = usuarioRepository.findByCorreo(userDetails.getUsername()).orElse(null);
        Usuario paciente = usuarioRepository.findById(pacienteId).orElse(null);

        // Validamos que exista la relación
        if (psicologo != null && paciente != null && citaRepository.existsByPsicologoAndPaciente(psicologo, paciente)) {
            Desempeno d = new Desempeno();
            d.setPaciente(paciente);
            d.setPsicologo(psicologo);
            d.setCalificacion(calificacion);
            d.setDescripcion(descripcion);
            d.setCondicion(condicion);
            d.setFechaHora(LocalDateTime.now());
            desempenoRepository.save(d);

            // MESAJE DE ÉXITO
            redirectAttributes.addFlashAttribute("mensaje", "¡Éxito! La evaluación se ha guardado correctamente.");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } else {
            // MENSAJE DE ERROR
            redirectAttributes.addFlashAttribute("mensaje", "Error: No se pudo guardar la evaluación. Verifique que el paciente tenga citas registradas.");
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        
        return "redirect:/dashboard-psicologo";
    }
}
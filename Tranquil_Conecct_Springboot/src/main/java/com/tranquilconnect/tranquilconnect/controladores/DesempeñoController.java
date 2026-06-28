package com.tranquilconnect.tranquilconnect.controladores;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.tranquilconnect.tranquilconnect.model.Usuario; 
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import com.tranquilconnect.tranquilconnect.repository.CitaRepository;
import com.tranquilconnect.tranquilconnect.repository.DesempenoRepository;
import org.springframework.ui.Model; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DesempeñoController {
    
    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private DesempenoRepository desempenoRepository;

    @GetMapping("/desempeno")
    public String mostrarpagina(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        
        Usuario usuario = null;
        
        if (userDetails != null) {
            String correo = userDetails.getUsername(); 
            usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        }
        
        model.addAttribute("usuario", usuario);

        // Lógica para pacientes
        if (usuario != null && (usuario.getRol() == null || !usuario.getRol().equalsIgnoreCase("PSICOLOGO"))) {
            List<com.tranquilconnect.tranquilconnect.model.Desempeno> notas = desempenoRepository.findByPaciente(usuario);
            model.addAttribute("notas", notas);
            
            List<String> labels = notas.stream()
                .map(n -> n.getFechaHora() != null ? n.getFechaHora().toLocalDate().toString() : "-")
                .collect(Collectors.toList());
            List<Integer> data = notas.stream()
                .map(n -> n.getCalificacion() != null ? n.getCalificacion() : 0)
                .collect(Collectors.toList());
            
            model.addAttribute("chartLabels", labels);
            model.addAttribute("chartData", data);
        }

        // Lógica para psicólogos
        if (usuario != null && usuario.getRol() != null && usuario.getRol().equalsIgnoreCase("PSICOLOGO")) {
            List<com.tranquilconnect.tranquilconnect.model.Cita> citas = citaRepository.findByPsicologo(usuario);
            List<com.tranquilconnect.tranquilconnect.model.Usuario> pacientes = citas.stream()
                .map(c -> c.getPaciente())
                .distinct()
                .collect(Collectors.toList());
            model.addAttribute("misPacientes", pacientes);
        }

        return "desempeno";
    }
}
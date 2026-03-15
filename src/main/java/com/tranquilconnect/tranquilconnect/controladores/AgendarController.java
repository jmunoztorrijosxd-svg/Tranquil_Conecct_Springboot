package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Cita;
import com.tranquilconnect.tranquilconnect.model.Usuario; 
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; 
import com.tranquilconnect.tranquilconnect.repository.CitaRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List; 

@Controller
public class AgendarController {

    @Autowired 
    private UsuarioRepository usuarioRepository; 

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping("/agendar")
    public String mostrarpagina(Model model, @AuthenticationPrincipal UserDetails userDetails) { 
        Usuario usuarioLogueado = null;
        if (userDetails != null) {
            String correo = userDetails.getUsername(); 
            usuarioLogueado = usuarioRepository.findByCorreo(correo).orElse(null);
            
            if (usuarioLogueado != null) {
                List<Cita> misCitas = citaRepository.findByPaciente(usuarioLogueado);
                model.addAttribute("misCitas", misCitas);
            }
        }
        model.addAttribute("usuario", usuarioLogueado);
        
        List<Usuario> listaPsicologos = usuarioRepository.findByRolAndEstadoValidacion("PSICOLOGO", "APROBADO");
        model.addAttribute("psicologos", listaPsicologos);
        
        return "agendar";
    }

    @PostMapping("/confirmar-cita")
    public String guardarCita(@RequestParam("psicologoId") Long psicologoId, // Cambiado a Long
                             @RequestParam("fecha") String fechaStr, 
                             Authentication auth) {
        
        if (auth == null) return "redirect:/login";

        String correo = auth.getName();
        Usuario paciente = usuarioRepository.findByCorreo(correo)
                            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        
        // Ahora coincidirá con el Repositorio que espera Long
        Usuario psicologo = usuarioRepository.findById(psicologoId)
                            .orElseThrow(() -> new RuntimeException("Psicólogo no encontrado"));

        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente);
        nuevaCita.setPsicologo(psicologo);
        
        try {
            nuevaCita.setFecha(LocalDate.parse(fechaStr)); 
        } catch (Exception e) {
            return "redirect:/agendar?error_fecha";
        }
        
        nuevaCita.setEstado("PENDIENTE");
        citaRepository.save(nuevaCita);

        return "redirect:/agendar?exito";
    }

    @GetMapping("/mis-citas-profesional")
    public String verCitasProfesional(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";

        String correo = userDetails.getUsername();
        Usuario psicologo = usuarioRepository.findByCorreo(correo)
                            .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        List<Cita> citasRecibidas = citaRepository.findByPsicologo(psicologo);
        
        model.addAttribute("usuario", psicologo);
        model.addAttribute("citas", citasRecibidas);
        
        return "citas-psicologo"; 
    }

    @PostMapping("/aprobar-cita")
    public String aprobarCita(@RequestParam("citaId") Long citaId) { // Cambiado a Long
        Cita cita = citaRepository.findById(citaId)
                    .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        cita.setEstado("APROBADA");
        citaRepository.save(cita);
        
        return "redirect:/mis-citas-profesional?aprobada";
    }
}
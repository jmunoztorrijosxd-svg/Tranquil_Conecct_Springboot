package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.Cita;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Cambiamos Integer por Long para que coincida con el resto del proyecto
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    // Método para que el psicólogo recupere las citas que le han agendado
    List<Cita> findByPsicologo(Usuario psicologo);
    
    // Método para que el paciente recupere las citas que él ha solicitado
    List<Cita> findByPaciente(Usuario paciente);
}
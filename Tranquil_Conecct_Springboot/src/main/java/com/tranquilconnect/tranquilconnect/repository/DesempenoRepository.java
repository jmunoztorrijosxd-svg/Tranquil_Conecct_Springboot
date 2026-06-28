package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.Desempeno;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DesempenoRepository extends JpaRepository<Desempeno, Long> {
    List<Desempeno> findByPaciente(Usuario paciente);
    List<Desempeno> findByPsicologo(Usuario psicologo);
}

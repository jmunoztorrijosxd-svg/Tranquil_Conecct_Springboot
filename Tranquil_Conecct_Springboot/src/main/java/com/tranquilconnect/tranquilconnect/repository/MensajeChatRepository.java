package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.MensajeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {
    
    // Este método buscará todos los mensajes de un grupo específico
    // Spring Boot deduce la consulta SQL solo por el nombre del método (Query Method)
    List<MensajeChat> findByIdGrupoOrderByFechaEnvioAsc(Integer idGrupo);
    
}
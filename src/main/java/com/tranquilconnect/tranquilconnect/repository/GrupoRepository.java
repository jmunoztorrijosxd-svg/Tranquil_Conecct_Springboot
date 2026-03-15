package com.tranquilconnect.tranquilconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tranquilconnect.tranquilconnect.model.Grupo;
import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> { // Cambiado a Long

    List<Grupo> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
        String nombreKeyword, 
        String descripcionKeyword
    );
}
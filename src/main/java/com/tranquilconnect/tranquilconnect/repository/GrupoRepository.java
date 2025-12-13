package com.tranquilconnect.tranquilconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranquilconnect.tranquilconnect.model.Grupo;
import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> { 
    // NOTA IMPORTANTE: Cambié <Grupo, Long> a <Grupo, Integer> asumiendo que id_grupo es INTEGER.
    // Si tu id_grupo es de tipo Long, mantén <Grupo, Long>.

    // -------------------------------------------------------------------------
    // MÉTODO PERSONALIZADO PARA BÚSQUEDA (Necesario para el Controller/HTML)
    // -------------------------------------------------------------------------

    /**
     * Busca Grupos cuyo nombre o descripción contenga la palabra clave (keyword).
     * El 'Containing' hace la búsqueda con LIKE %keyword%
     * El 'IgnoreCase' hace la búsqueda sin distinguir mayúsculas/minúsculas.
     */
    List<Grupo> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
        String nombreKeyword, 
        String descripcionKeyword
    );
    
}
package com.tranquilconnect.tranquilconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tranquilconnect.tranquilconnect.model.Grupo;
import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    // Tu método original (se mantiene igual)
    List<Grupo> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
        String nombreKeyword, 
        String descripcionKeyword
    );

    /**
     * Nuevo método para facilitar el filtrado desde el controlador.
     * Busca la palabra clave en el nombre o en la descripción ignorando mayúsculas/minúsculas.
     */
    @Query("SELECT g FROM Grupo g WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(g.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(g.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Grupo> findByFiltros(@Param("keyword") String keyword);
}
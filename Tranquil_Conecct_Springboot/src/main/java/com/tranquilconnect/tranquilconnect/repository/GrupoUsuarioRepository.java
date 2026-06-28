package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.GrupoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoUsuarioRepository
        extends JpaRepository<GrupoUsuario, Long> {

    List<GrupoUsuario> findByGrupo_IdGrupo(Long idGrupo);

    boolean existsByGrupo_IdGrupoAndUsuario_Id(
            Long idGrupo,
            Long idUsuario
    );
}
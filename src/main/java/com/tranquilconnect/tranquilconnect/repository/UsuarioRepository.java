package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> { 

    Optional<Usuario> findByCorreo(String correo);

    // Método para filtrar por Rol y Estado (lo que usa Julian)
    List<Usuario> findByRolAndEstadoValidacion(String rol, String estadoValidacion);

    // ⬇️ MÉTODO PARA ACTUALIZACIÓN FORZOSA DE CONTRASEÑA ⬇️
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.password = ?2 WHERE u.correo = ?1")
    void actualizarPasswordDirecto(String correo, String nuevaClave);
}
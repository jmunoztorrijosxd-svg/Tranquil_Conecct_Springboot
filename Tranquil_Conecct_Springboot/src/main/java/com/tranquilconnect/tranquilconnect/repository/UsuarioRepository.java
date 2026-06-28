package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> { 

    Optional<Usuario> findByCorreo(String correo);

    // Método para filtrar por Rol y Estado (lo que usa Julian)
    List<Usuario> findByRolAndEstadoValidacion(String rol, String estadoValidacion);

    // ⬇️ NUEVO MÉTODO PARA FILTRADO DINÁMICO (Keyword, Rol, Género) ⬇️
    @Query("SELECT u FROM Usuario u WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR u.nombre LIKE %:keyword% OR u.correo LIKE %:keyword% OR u.telefono LIKE %:keyword%) AND " +
           "(:rol IS NULL OR :rol = '' OR u.rol = :rol) AND " +
           "(:genero IS NULL OR :genero = '' OR u.genero = :genero)")
    List<Usuario> findByFiltros(@Param("keyword") String keyword, 
                                @Param("rol") String rol, 
                                @Param("genero") String genero);

    // ⬇️ MÉTODO PARA ACTUALIZACIÓN FORZOSA DE CONTRASEÑA ⬇️
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.password = ?2 WHERE u.correo = ?1")
    void actualizarPasswordDirecto(String correo, String nuevaClave);
}
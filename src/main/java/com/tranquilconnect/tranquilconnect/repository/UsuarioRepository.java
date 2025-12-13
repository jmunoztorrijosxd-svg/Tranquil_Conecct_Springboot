package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // ⬅️ ¡NUEVO IMPORT!
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Ahora extiende de JpaSpecificationExecutor<Usuario>
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> { 

    Optional<Usuario> findByCorreo(String correo);
}
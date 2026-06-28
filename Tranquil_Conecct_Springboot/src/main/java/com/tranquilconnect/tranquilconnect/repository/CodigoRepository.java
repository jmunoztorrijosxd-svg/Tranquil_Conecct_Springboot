package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.CodigoRecuperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface CodigoRepository extends JpaRepository<CodigoRecuperacion, Long> {
    
    Optional<CodigoRecuperacion> findByCorreoAndCodigo(String correo, String codigo);
    
    @Transactional
    void deleteByCorreo(String correo);
}
package com.tranquilconnect.tranquilconnect.repository;

import com.tranquilconnect.tranquilconnect.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsicologoRepository extends JpaRepository<Psicologo, Long> {
    // Spring Data JPA ya proporciona métodos como findAll()
}
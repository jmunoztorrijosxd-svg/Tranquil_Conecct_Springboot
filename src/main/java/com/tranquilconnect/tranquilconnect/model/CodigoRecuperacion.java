package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "codigo_recuperacion")
public class CodigoRecuperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;
    private String codigo;
    private LocalDateTime expiracion;

    public CodigoRecuperacion() {}

    public CodigoRecuperacion(String correo, String codigo) {
        this.correo = correo;
        this.codigo = codigo;
        this.expiracion = LocalDateTime.now().plusMinutes(15); // Expira en 15 min
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDateTime getExpiracion() { return expiracion; }
    public void setExpiracion(LocalDateTime expiracion) { this.expiracion = expiracion; }
}
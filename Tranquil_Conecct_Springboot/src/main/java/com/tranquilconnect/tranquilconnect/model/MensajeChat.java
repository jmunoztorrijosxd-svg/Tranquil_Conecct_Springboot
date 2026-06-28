package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes_chat") // Debe ser el nombre exacto que tiene la tabla en phpMyAdmin
public class MensajeChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_grupo")
    private Integer idGrupo;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "imagen_url", nullable = true)
    private String imagenUrl;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    // --- Constructor vacío (Obligatorio para JPA) ---
    public MensajeChat() {}

    // --- Getters y Setters (Para que Spring pueda leer los datos) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
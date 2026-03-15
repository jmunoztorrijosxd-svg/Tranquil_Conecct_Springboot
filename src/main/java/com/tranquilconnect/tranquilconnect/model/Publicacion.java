package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String imagen;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fecha;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Publicacion() {
        this.fecha = LocalDateTime.now();
    }

    public Publicacion(String contenido, String imagen, Usuario usuario) {
        this.contenido = contenido;
        this.imagen = imagen;
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    public String getImagen() {
        return imagen;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
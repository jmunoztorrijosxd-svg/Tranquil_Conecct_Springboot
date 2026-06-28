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

    // 🌟 NUEVO: Atributos transitorios para los conteos de reacciones
    // La anotación @Transient evita que Hibernate intente buscar estas columnas en la tabla 'publicaciones'
    @Transient
    private int transientLoveCount;

    @Transient
    private int transientCareCount;

    @Transient
    private int transientHahaCount;

    // ===== CONSTRUCTORES =====
    public Publicacion() {
        this.fecha = LocalDateTime.now();
    }

    public Publicacion(String contenido, String imagen, Usuario usuario) {
        this.contenido = contenido;
        this.imagen = imagen;
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
    }

    // ===== GETTERS Y SETTERS =====
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

    // 🌟 NUEVO: Getters y Setters para los atributos transitorios
    public int getTransientLoveCount() {
        return transientLoveCount;
    }

    public void setTransientLoveCount(int transientLoveCount) {
        this.transientLoveCount = transientLoveCount;
    }

    public int getTransientCareCount() {
        return transientCareCount;
    }

    public void setTransientCareCount(int transientCareCount) {
        this.transientCareCount = transientCareCount;
    }

    public int getTransientHahaCount() {
        return transientHahaCount;
    }

    public void setTransientHahaCount(int transientHahaCount) {
        this.transientHahaCount = transientHahaCount;
    }
}
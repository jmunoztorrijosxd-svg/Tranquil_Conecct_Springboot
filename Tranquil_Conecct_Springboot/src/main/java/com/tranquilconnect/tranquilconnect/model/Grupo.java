package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;

/**
 * Entidad que representa la tabla 'grupo' en la base de datos.
 * @author jmuno
 */ 
@Entity
@Table(name = "grupo")
public class Grupo {
    
    // --- 1. CLAVE PRIMARIA (ID) ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo", updatable = false, nullable = false)
    private Long idGrupo;

    // --- 2. CAMPOS DE LA TABLA ---
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "num_miembros")
    private Integer numMiembros = 1; // Valor por defecto inicial

    @Column(name = "motivo_salida")
    private String motivoSalida = "Sin motivo";

    @ManyToOne
    @JoinColumn(name = "id_usuario_creador")
    private Usuario creador;

    // --- 3. CONSTRUCTORES ---
    public Grupo() {
    }

    public Grupo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // --- 4. GETTERS Y SETTERS ---

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumMiembros() {
        return numMiembros;
    }

    public void setNumMiembros(Integer numMiembros) {
        this.numMiembros = numMiembros;
    }

    public String getMotivoSalida() {
        return motivoSalida;
    }

    public void setMotivoSalida(String motivoSalida) {
        this.motivoSalida = motivoSalida;
    }

    public Usuario getCreador() {
    return creador;
}

    public void setCreador(Usuario creador) {
    this.creador = creador;
}
}
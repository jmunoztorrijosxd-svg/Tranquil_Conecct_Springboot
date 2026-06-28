package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario") 
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String nombre;
    private String correo; 
    
    @Column(name = "contrasena") 
    private String password;
    
    private String genero; 
    private String telefono; 
    private String rol; 

    // --- NUEVOS CAMPOS PARA PSICÓLOGOS ---
    
    private String especialidad;

    @Column(name = "tarjeta_profesional")
    private String tarjetaProfesional;

    @Column(name = "estado_validacion")
    private String estadoValidacion = "PENDIENTE"; // Valor por defecto igual que en tu DB

    @Column(columnDefinition = "TEXT")
    private String experiencia; // Mapeado al campo 'experiencia' que ya tenías en DB

    // --- Constructor ---
    public Usuario() {
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // Getters y Setters de los campos nuevos
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTarjetaProfesional() { return tarjetaProfesional; }
    public void setTarjetaProfesional(String tarjetaProfesional) { this.tarjetaProfesional = tarjetaProfesional; }

    public String getEstadoValidacion() { return estadoValidacion; }
    public void setEstadoValidacion(String estadoValidacion) { this.estadoValidacion = estadoValidacion; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }
}
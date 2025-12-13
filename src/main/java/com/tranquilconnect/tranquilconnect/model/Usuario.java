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
    
    // El campo en la DB es 'contrasena'
    @Column(name = "contrasena") 
    private String password;
    
    private String genero; 
    
    private String telefono; 
    
    // 🚨 CAMBIO CLAVE: Mapear la nueva columna 'rol'
    private String rol; 
    
    // El campo 'experiencia' que tienes en la DB también debería estar aquí, si lo necesitas.

    public Usuario() {
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    // 🚨 AÑADIDO: Getter y Setter para 'rol'
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
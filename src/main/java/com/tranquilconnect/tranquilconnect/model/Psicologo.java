package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "psicologo")
public class Psicologo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psicologo_id")
    private Long id; 

    private String nombre;

    private String especialidad;
    
    private String email;

    // Constructor vacío (necesario para JPA)
    public Psicologo() {
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
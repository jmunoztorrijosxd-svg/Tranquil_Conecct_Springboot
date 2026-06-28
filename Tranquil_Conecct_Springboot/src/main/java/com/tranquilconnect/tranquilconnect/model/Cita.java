package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cita") 
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // SE CAMBIÓ DE Integer A Long

    // Relación con el Paciente (Usuario que agenda)
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    // Relación con el Psicólogo (Usuario elegido)
    @ManyToOne
    @JoinColumn(name = "psicologo_id", nullable = false)
    private Usuario psicologo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 20)
    private String estado; // PENDIENTE, APROBADA, CANCELADA

    // Constructor vacío (obligatorio para JPA)
    public Cita() {
    }

    // Getters y Setters actualizados con Long
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }

    public Usuario getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Usuario psicologo) {
        this.psicologo = psicologo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
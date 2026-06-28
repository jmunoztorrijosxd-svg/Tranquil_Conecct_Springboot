package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "desempeño")
public class Desempeno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_desempeño")
    private Long id;

    @Column(length = 500)
    private String descripcion;

    private Integer calificacion;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(length = 600)
    private String condicion;

    // Paciente al que se le asigna el desempeño
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario paciente;

    // Psicólogo que hizo la evaluación (nueva columna en la BD: psicologo_id)
    @ManyToOne
    @JoinColumn(name = "psicologo_id")
    private Usuario psicologo;

    public Desempeno() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }

    public Usuario getPaciente() { return paciente; }
    public void setPaciente(Usuario paciente) { this.paciente = paciente; }

    public Usuario getPsicologo() { return psicologo; }
    public void setPsicologo(Usuario psicologo) { this.psicologo = psicologo; }
}

package com.tranquilconnect.tranquilconnect.model;
import jakarta.persistence.*; // IMPORTANTE: Asegúrate de tener esta línea

/**
 *
 * @author jmuno
 */ 
@Entity
@Table(name = "grupo") // Especifica el nombre exacto de tu tabla en MySQL
public class Grupo {
    
    // --- 1. CLAVE PRIMARIA (ID) ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Long idGrupo; // Usamos Long para el ID

    // --- 2. CAMPOS DE LA TABLA ---
    
    private String nombre;

    private String descripcion;
    
    // Mapeo de columnas con nombres separados por guiones bajos
    @Column(name = "num_miembros")
    private Integer numMiembros;

    @Column(name = "motivo_salida")
    private String motivoSalida;


    // --- 3. CONSTRUCTOR (Necesario para JPA/Hibernate) ---
    public Grupo() {
    }

    // --- 4. GETTERS Y SETTERS (Para acceder y modificar los valores) ---

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
}
package com.tranquilconnect.tranquilconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grupo_usuario")
public class GrupoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // GETTERS Y SETTERS

    public Long getIdGrupoUsuario() {
        return idGrupoUsuario;
    }

    public void setIdGrupoUsuario(Long idGrupoUsuario) {
        this.idGrupoUsuario = idGrupoUsuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
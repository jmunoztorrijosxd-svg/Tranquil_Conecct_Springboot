package com.tranquilconnect.tranquilconnect.controladores;

import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import com.tranquilconnect.tranquilconnect.model.Grupo;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.model.GrupoUsuario;

import com.tranquilconnect.tranquilconnect.repository.GrupoRepository;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import com.tranquilconnect.tranquilconnect.repository.GrupoUsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.ui.Model; // Necesario para 'Model model'
import org.springframework.web.bind.annotation.PathVariable; // Para recibir el {id}
import org.springframework.security.core.userdetails.UserDetails; // Para el userDetails

@Controller
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    // Clave de seguridad para JWT del chatbot
    private final String SECRET_KEY =
            "esta_es_una_clave_muy_segura_y_larga_para_jwt_123456";

    /**
     * Mostrar página de grupos
     */
    @GetMapping("/grupos")
    public String mostrarPaginaGrupos(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        if (userDetails != null) {

            String correo = userDetails.getUsername();

            Optional<Usuario> usuarioOpt =
                    usuarioRepository.findByCorreo(correo);

            usuarioOpt.ifPresent(usuario -> {

                try {

                    // Generar token JWT
                    SecretKey key = Keys.hmacShaKeyFor(
                            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
                    );

                    String token = Jwts.builder()
                            .setSubject(usuario.getId().toString())
                            .setIssuedAt(new Date())
                            .signWith(key, SignatureAlgorithm.HS256)
                            .compact();

                    model.addAttribute("usuario", usuario);
                    model.addAttribute("token_seguro", token);

                } catch (Exception e) {

                    System.err.println(
                            "❌ Error generando token: "
                                    + e.getMessage()
                    );
                }

            });
        }

        // Obtener grupos
        List<Grupo> lista = grupoRepository.findAll();

        model.addAttribute("listaDeGrupos", lista);

        return "grupos";
    }

    /**
     * Mostrar formulario crear grupo
     */
    @GetMapping("/grupos/nuevo")
    public String mostrarFormularioCrear(Model model) {

        model.addAttribute("nuevoGrupo", new Grupo());

        return "crear_grupo";
    }

    /**
     * Guardar grupo
     */
    @PostMapping("/grupos/guardar")
    public String guardarGrupo(
            @ModelAttribute("nuevoGrupo") Grupo grupo,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        try {

            grupo.setIdGrupo(null);

            // Valor por defecto
            if (grupo.getNumMiembros() == null) {
                grupo.setNumMiembros(1);
            }

            // Usuario autenticado
            if (userDetails != null) {

                String correo = userDetails.getUsername();

                Usuario usuario = usuarioRepository
                        .findByCorreo(correo)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuario no encontrado"
                                )
                        );

                // Guardar creador
                grupo.setCreador(usuario);
            }

            grupoRepository.save(grupo);

            System.out.println("✅ Grupo creado correctamente");

            return "redirect:/grupos?exito";

        } catch (Exception e) {

            System.err.println(
                    "❌ Error guardando grupo: "
                            + e.getMessage()
            );

            e.printStackTrace();

            return "redirect:/grupos/nuevo?error";
        }
    }

    /**
     * Eliminar grupo
     * SOLO el creador puede eliminarlo
     */
    @GetMapping("/grupos/eliminar/{id}")
    public String eliminarGrupo(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        try {

            Grupo grupo = grupoRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Grupo no encontrado"
                            )
                    );

            // Verificar usuario autenticado
            if (userDetails == null) {
                return "redirect:/login";
            }

            String correo = userDetails.getUsername();

            Usuario usuario = usuarioRepository
                    .findByCorreo(correo)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Usuario no encontrado"
                            )
                    );

            // SOLO el creador puede eliminar
            if (grupo.getCreador() != null &&
                    grupo.getCreador()
                            .getId()
                            .equals(usuario.getId())) {

                grupoRepository.delete(grupo);

                System.out.println(
                        "✅ Grupo eliminado correctamente"
                );

            } else {

                System.out.println(
                        "❌ Usuario sin permisos para eliminar"
                );

            }

        } catch (Exception e) {

            System.err.println(
                    "❌ Error Comicando eliminación de grupo: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return "redirect:/grupos";
    }

    /**
     * UNIRSE A UN GRUPO
     * Registra la relación en la tabla grupo_usuario antes de ir al chat
     */
    @GetMapping("/grupos/unirse/{id}")
public String unirseAGrupo(
        @PathVariable Long id, 
        @AuthenticationPrincipal UserDetails userDetails,
        Model model // Agrega el Model aquí
) {
    
    if (userDetails == null) {
        return "redirect:/login";
    }

    try {
        String correo = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        boolean yaEsMiembro = grupoUsuarioRepository.existsByGrupo_IdGrupoAndUsuario_Id(id, usuario.getId());

        if (!yaEsMiembro) {
            GrupoUsuario nuevoMiembro = new GrupoUsuario();
            nuevoMiembro.setGrupo(grupo);
            nuevoMiembro.setUsuario(usuario);
            grupoUsuarioRepository.save(nuevoMiembro);
        }

        // --- AQUÍ ESTÁ LA MAGIA ---
        // En lugar de redirigir a una URL, pasamos los datos a la vista "chat"
        model.addAttribute("idGrupo", id);
        model.addAttribute("nombreGrupo", grupo.getNombre());
        model.addAttribute("usuario", usuario);
        
        return "chat"; // Esto cargará tu archivo chat.html con el iframe adentro
        
    } catch (Exception e) {
        return "redirect:/grupos?error";
    }
}

    /**
     * VER MIEMBROS DEL GRUPO (CORREGIDO PARA ACCESO DESDE DJANGO)
     */
    @GetMapping("/grupo/{id}/miembros")
    public String verMiembrosGrupo(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        System.out.println("ENTRANDO A MIEMBROS");
        // 1. Obtener los miembros asignados al grupo por base de datos
        List<GrupoUsuario> miembros = grupoUsuarioRepository.findByGrupo_IdGrupo(id);
        model.addAttribute("miembros", miembros);

        // 2. Control seguro para el Navbar compartido de Thymeleaf
        if (userDetails != null) {
            // Si por alguna razón mantiene la sesión en Spring
            usuarioRepository.findByCorreo(userDetails.getUsername())
                    .ifPresent(usuario -> model.addAttribute("usuario", usuario));
        } else {
            // 💡 SOLUCIÓN: Si viene saltando desde Django (Anónimo en Spring Boot)
            // Creamos un perfil temporal controlado para que las plantillas no tiren NullPointerException
            Usuario usuarioInvitado = new Usuario();
            usuarioInvitado.setNombre("Conectado");
            usuarioInvitado.setRol("CONECTADO");
            
            model.addAttribute("usuario", usuarioInvitado);
        }

        return "miembros_grupo";
    }

}
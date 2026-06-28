package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Publicacion;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.PublicacionRepository;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Inyección para interactuar directamente con la tabla de reacciones de forma nativa
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ===== MOSTRAR FORO =====
    @GetMapping("/forosocial")
    public String verForo(Model model,
                          @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = null;

        if (userDetails != null) {
            String correo = userDetails.getUsername();
            usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        }

        List<Publicacion> publicaciones =
                publicacionRepository.findAllByOrderByFechaDesc();

        // 🌟 CORRECCIÓN: Consultar y asignar los conteos iniciales desde la BD para cada post
        for (Publicacion post : publicaciones) {
            String sqlContar = "SELECT COUNT(*) FROM reacciones WHERE publicacion_id = ? AND tipo_reaccion = ?";
            
            int loveCount = jdbcTemplate.queryForObject(sqlContar, Integer.class, post.getId(), "love");
            int careCount = jdbcTemplate.queryForObject(sqlContar, Integer.class, post.getId(), "care");
            int hahaCount = jdbcTemplate.queryForObject(sqlContar, Integer.class, post.getId(), "haha");

            // Se asignan a las propiedades transitorias (@Transient) del modelo
            post.setTransientLoveCount(loveCount);
            post.setTransientCareCount(careCount);
            post.setTransientHahaCount(hahaCount);
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("publicaciones", publicaciones);
        model.addAttribute("nuevaPublicacion", new Publicacion());

        return "forosocial";
    }

    // ===== PUBLICAR =====
    @PostMapping("/publicar")
    public String guardarPublicacion(@ModelAttribute Publicacion publicacion,
                                     @RequestParam("file") MultipartFile imagenFile,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     RedirectAttributes ra) {

        // Usuario logueado
        if (userDetails != null) {

            String correo = userDetails.getUsername();

            Usuario usuario =
                    usuarioRepository.findByCorreo(correo).orElse(null);

            if (usuario != null) {
                publicacion.setUsuario(usuario);
            }
        }

        // Guardar imagen/video
        if (!imagenFile.isEmpty()) {

            String rutaRelativa = "uploads/";

            Path directorioImagenes = Paths.get(rutaRelativa);

            String rutaAbsoluta =
                    directorioImagenes.toFile().getAbsolutePath();

            try {

                if (!Files.exists(directorioImagenes)) {
                    Files.createDirectories(directorioImagenes);
                }

                String nombreUnico =
                        UUID.randomUUID().toString()
                                + "_"
                                + imagenFile.getOriginalFilename();

                Path rutaCompleta =
                        Paths.get(rutaAbsoluta + "/" + nombreUnico);

                Files.write(rutaCompleta, imagenFile.getBytes());

                publicacion.setImagen(nombreUnico);

            } catch (IOException e) {

                System.out.println("Error al subir archivo: "
                        + e.getMessage());
            }
        }

        publicacionRepository.save(publicacion);
        ra.addFlashAttribute("mensaje", "Publicación creada con éxito.");
        ra.addFlashAttribute("toastType", "success");

        return "redirect:/forosocial";
    }

    // ===== ELIMINAR PUBLICACIÓN =====
    @PostMapping("/eliminar-publicacion/{id}")
    public String eliminarPublicacion(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      RedirectAttributes ra) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        String correo = userDetails.getUsername();

        Usuario usuario =
                usuarioRepository.findByCorreo(correo).orElse(null);

        Publicacion post =
                publicacionRepository.findById(id).orElse(null);

        // SOLO EL DUEÑO PUEDE ELIMINAR
        if (post != null
                && usuario != null
                && post.getUsuario().getId().equals(usuario.getId())) {

            // Eliminar archivo físico si existe
            if (post.getImagen() != null
                    && !post.getImagen().isEmpty()) {

                try {

                    Path rutaArchivo =
                            Paths.get("uploads/" + post.getImagen());

                    Files.deleteIfExists(rutaArchivo);

                } catch (IOException e) {

                    System.out.println("Error eliminando archivo: "
                            + e.getMessage());
                }
            }

            // Eliminar publicación
            publicacionRepository.delete(post);
            ra.addFlashAttribute("mensaje", "Publicación eliminada correctamente.");
            ra.addFlashAttribute("toastType", "success");
        }

        return "redirect:/forosocial";
    }

    // ===== MOSTRAR FORMULARIO EDITAR =====
    @GetMapping("/editar-publicacion/{id}")
    public String editarPublicacion(@PathVariable Long id,
                                    Model model,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        String correo = userDetails.getUsername();

        Usuario usuario =
                usuarioRepository.findByCorreo(correo).orElse(null);

        Publicacion post =
                publicacionRepository.findById(id).orElse(null);

        // SOLO EL DUEÑO PUEDE EDITAR
        if (post == null
                || usuario == null
                || !post.getUsuario().getId().equals(usuario.getId())) {

            return "redirect:/forosocial";
        }

        model.addAttribute("publicacion", post);

        return "editar-publicacion"; 
    }

    // ===== ACTUALIZAR PUBLICACIÓN =====
    @PostMapping("/actualizar-publicacion/{id}")
    public String actualizarPublicacion(@PathVariable Long id,
                                        @RequestParam("contenido") String contenido,
                                        @RequestParam(value = "file", required = false) MultipartFile imagenFile,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        RedirectAttributes ra) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        String correo = userDetails.getUsername();

        Usuario usuario =
                usuarioRepository.findByCorreo(correo).orElse(null);

        Publicacion post =
                publicacionRepository.findById(id).orElse(null);

        // SOLO EL DUEÑO PUEDE ACTUALIZAR
        if (post != null
                && usuario != null
                && post.getUsuario().getId().equals(usuario.getId())) {

            // 1. Actualizamos el texto
            post.setContenido(contenido);

            // 2. Verificamos si el usuario subió un archivo nuevo
            if (imagenFile != null && !imagenFile.isEmpty()) {

                // OPCIONAL: Borrar la foto/video anterior del disco para no acumular basura
                if (post.getImagen() != null && !post.getImagen().isEmpty()) {
                    try {
                        Path rutaArchivoAnterior = Paths.get("uploads/" + post.getImagen());
                        Files.deleteIfExists(rutaArchivoAnterior);
                    } catch (IOException e) {
                        System.out.println("Error al borrar archivo anterior: " + e.getMessage());
                    }
                }

                // Guardar el nuevo archivo físico en la carpeta uploads
                String rutaRelativa = "uploads/";
                Path directorioImagenes = Paths.get(rutaRelativa);
                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                try {
                    if (!Files.exists(directorioImagenes)) {
                        Files.createDirectories(directorioImagenes);
                    }

                    String nombreUnico = UUID.randomUUID().toString()
                            + "_"
                            + imagenFile.getOriginalFilename();

                    Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + nombreUnico);
                    Files.write(rutaCompleta, imagenFile.getBytes());

                    // Asignamos el nuevo nombre del archivo al post
                    post.setImagen(nombreUnico);

                } catch (IOException e) {
                    System.out.println("Error al subir archivo en edición: " + e.getMessage());
                }
            }

            // 3. Guardamos todos los cambios en la base de datos
            publicacionRepository.save(post);
            ra.addFlashAttribute("mensaje", "Publicación actualizada correctamente.");
            ra.addFlashAttribute("toastType", "success");
        }

        return "redirect:/forosocial";
    }

    // ===== REGISTRAR REACCIÓN (AJAX) =====
    @PostMapping("/api/publicacion/reaccionar")
    @ResponseBody
    public Map<String, Object> registrarReaccion(
            @RequestParam("publicacion_id") Long publicacionId,
            @RequestParam("tipo_reaccion") String tipoReaccion,
            @AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> respuesta = new HashMap<>();

        if (userDetails == null) {
            respuesta.put("status", "error");
            respuesta.put("message", "Usuario no autenticado");
            return respuesta;
        }

        String correo = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            respuesta.put("status", "error");
            respuesta.put("message", "Usuario no encontrado");
            return respuesta;
        }

        Long usuarioId = usuario.getId();

        try {
            // 1. Validar la existencia de una reacción previa en la BD compartida
            String sqlBuscar = "SELECT tipo_reaccion FROM reacciones WHERE usuario_id = ? AND publicacion_id = ?";
            List<String> reaccionesPrevias = jdbcTemplate.query(sqlBuscar, (rs, rowNum) -> rs.getString("tipo_reaccion"), usuarioId, publicacionId);

            String accion = "";

            if (!reaccionesPrevias.isEmpty()) {
                String reaccionActual = reaccionesPrevias.get(0);
                if (reaccionActual.equals(tipoReaccion)) {
                    // Si el usuario presiona el mismo emoji que ya tenía, se retira de la BD
                    String sqlDelete = "DELETE FROM reacciones WHERE usuario_id = ? AND publicacion_id = ?";
                    jdbcTemplate.update(sqlDelete, usuarioId, publicacionId);
                    accion = "eliminada";
                } else {
                    // Si cambia a un emoji diferente (ej: de ❤️ a 😂), se actualiza la tupla
                    String sqlUpdate = "UPDATE reacciones SET tipo_reaccion = ? WHERE usuario_id = ? AND publicacion_id = ?";
                    jdbcTemplate.update(sqlUpdate, tipoReaccion, usuarioId, publicacionId);
                    accion = "actualizada";
                }
            } else {
                // Registro inicial de reacción
                String sqlInsert = "INSERT INTO reacciones (publicacion_id, usuario_id, tipo_reaccion) VALUES (?, ?, ?)";
                jdbcTemplate.update(sqlInsert, publicacionId, usuarioId, tipoReaccion);
                accion = "creada";
            }

            // 2. Consultar conteos actuales agrupados por emoji para refrescar el frontend sin recargas
            String sqlContar = "SELECT COUNT(*) FROM reacciones WHERE publicacion_id = ? AND tipo_reaccion = ?";
            int loveCount = jdbcTemplate.queryForObject(sqlContar, Integer.class, publicacionId, "love");
            int careCount = jdbcTemplate.queryForObject(sqlContar, Integer.class, publicacionId, "care");
            int hahaCount = jdbcTemplate.queryForObject(sqlContar, Integer.class, publicacionId, "haha");

            Map<String, Integer> conteos = new HashMap<>();
            conteos.put("love", loveCount);
            conteos.put("care", careCount);
            conteos.put("haha", hahaCount);

            respuesta.put("status", "success");
            respuesta.put("accion", accion);
            respuesta.put("conteos", conteos);

        } catch (Exception e) {
            respuesta.put("status", "error");
            respuesta.put("message", e.getMessage());
        }

        return respuesta;
    }
}
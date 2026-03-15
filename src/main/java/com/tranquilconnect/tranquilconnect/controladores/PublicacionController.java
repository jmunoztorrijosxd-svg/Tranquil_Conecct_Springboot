package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Publicacion;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.PublicacionRepository;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar foro con usuario logueado y publicaciones ORDENADAS POR FECHA DESCENDENTE
    @GetMapping("/forosocial")
    public String verForo(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = null;

        if (userDetails != null) {
            String correo = userDetails.getUsername();
            usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        }

        // CAMBIO AQUÍ: Traer todas las publicaciones ordenadas de la más reciente a la más antigua
        List<Publicacion> publicaciones = publicacionRepository.findAllByOrderByFechaDesc();

        model.addAttribute("usuario", usuario);
        model.addAttribute("publicaciones", publicaciones);
        model.addAttribute("nuevaPublicacion", new Publicacion());

        return "forosocial";
    }

    // Guardar publicación con manejo de imagen
    @PostMapping("/publicar")
    public String guardarPublicacion(@ModelAttribute Publicacion publicacion,
                                     @RequestParam("file") MultipartFile imagenFile, 
                                     @AuthenticationPrincipal UserDetails userDetails) {

        // 1. Asignar el usuario logueado a la publicación
        if (userDetails != null) {
            String correo = userDetails.getUsername();
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
            if (usuario != null) {
                publicacion.setUsuario(usuario);
            }
        }

        // 2. Lógica para procesar y guardar la imagen físicamente
        if (!imagenFile.isEmpty()) {
            // Definimos la ruta de la carpeta (se creará en la raíz de tu proyecto)
            String rutaRelativa = "uploads/";
            Path directorioImagenes = Paths.get(rutaRelativa);
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                // Crear la carpeta si no existe
                if (!Files.exists(directorioImagenes)) {
                    Files.createDirectories(directorioImagenes);
                }

                // Crear un nombre único para el archivo (evita que se sobrescriban fotos con el mismo nombre)
                String nombreUnico = UUID.randomUUID().toString() + "_" + imagenFile.getOriginalFilename();
                
                // Guardar el archivo en el sistema de archivos
                Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + nombreUnico);
                Files.write(rutaCompleta, imagenFile.getBytes());

                // 3. Guardar solo el NOMBRE del archivo en el campo VARCHAR de la DB
                publicacion.setImagen(nombreUnico);

            } catch (IOException e) {
                System.out.println("Error al subir la imagen: " + e.getMessage());
                // Aquí podrías manejar el error o redirigir con un mensaje de error
            }
        }

        // 4. Guardar el objeto en la base de datos
        publicacionRepository.save(publicacion);

        return "redirect:/forosocial";
    }
}
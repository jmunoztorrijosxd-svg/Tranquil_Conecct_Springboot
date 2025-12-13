package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
// OJO: Se elimina la inyección de PasswordEncoder
// import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🚨 ELIMINADO: Ya no inyectamos PasswordEncoder
    // @Autowired
    // private PasswordEncoder passwordEncoder; 

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
        @ModelAttribute("usuario") Usuario usuario,
        Model model
    ) {

        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            model.addAttribute(
                    "error",
                    "El correo electrónico ya está registrado. Por favor, inicia sesión."
            );
            return "registro";
        }

        try {
            // 🚨 CAMBIO CLAVE: Se ELIMINA el cifrado de la contraseña.
            // La contraseña se guarda en texto plano (como está en el objeto 'usuario').
            // Nota: El setter en Usuario.java debe coincidir con el campo en el formulario (contrasena o password).
            
            // 🚨 Asignar el rol por defecto (USUARIO)
            usuario.setRol("USUARIO");

            usuarioRepository.save(usuario);

            // Redirección exitosa
            return "redirect:/login?registroExito=true";

        } catch (Exception e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            model.addAttribute(
                    "error",
                    "Ocurrió un error inesperado al intentar registrarte."
            );
            return "registro";
        }
    }
}
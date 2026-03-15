package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.model.CodigoRecuperacion;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import com.tranquilconnect.tranquilconnect.repository.CodigoRepository;
import com.tranquilconnect.tranquilconnect.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class RecuperarClaveController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CodigoRepository codigoRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/recuperar-clave")
    public String mostrarPaginaRecuperar() {
        return "recuperar-clave";
    }

    @PostMapping("/enviar-codigo")
    @Transactional
    public String enviarCodigo(@RequestParam String correo, Model model) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario != null) {
            String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
            
            // Guardar en la base de datos (borramos si había uno viejo)
            codigoRepository.deleteByCorreo(correo);
            codigoRepository.save(new CodigoRecuperacion(correo, codigo));

            // Enviar correo
            String mensaje = "Tu código de seguridad es: " + codigo;
            emailService.enviarCorreo(correo, "Restablecer Contraseña", mensaje);

            model.addAttribute("correo", correo);
            model.addAttribute("paso2", true);
            return "recuperar-clave";
        } else {
            model.addAttribute("error", "El correo no está registrado.");
            return "recuperar-clave";
        }
    }

    @PostMapping("/verificar-y-cambiar")
    @Transactional
    public String verificarYCambiar(@RequestParam String correo, 
                                    @RequestParam String codigo, 
                                    @RequestParam String nuevaClave, 
                                    Model model) {
        
        // Verificar si el código existe en la BD
        var tokenValido = codigoRepository.findByCorreoAndCodigo(correo, codigo);

        if (tokenValido.isPresent()) {
            // Actualizar password directamente en la tabla usuario
            usuarioRepository.actualizarPasswordDirecto(correo, nuevaClave);
            
            // Borrar el código para que no se use de nuevo
            codigoRepository.deleteByCorreo(correo);
            
            return "redirect:/login?claveActualizada";
        }

        model.addAttribute("error", "Código incorrecto o expirado.");
        model.addAttribute("correo", correo);
        model.addAttribute("paso2", true);
        return "recuperar-clave";
    }
}
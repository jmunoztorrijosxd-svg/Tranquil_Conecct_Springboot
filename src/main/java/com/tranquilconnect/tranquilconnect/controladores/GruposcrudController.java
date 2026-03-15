package com.tranquilconnect.tranquilconnect.controladores; // Verifica que esta ruta sea correcta

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.tranquilconnect.tranquilconnect.repository.GrupoRepository;
import java.util.List;
import com.tranquilconnect.tranquilconnect.model.Grupo;

@Controller
public class GruposcrudController {

    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping("/gruposcrud") 
    public String verGrupos(Model model) {
        List<Grupo> lista = grupoRepository.findAll();
        model.addAttribute("listaDeGrupos", lista);
        return "gruposcrud"; // Debe existir un archivo src/main/resources/templates/gruposcrud.html
    }
}
package com.tranquilconnect.tranquilconnect.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GruposcrudController {

    @GetMapping("/gruposcrud") // URL a la que se accede
    public String verGrupos() {
        return "gruposcrud"; // nombre del archivo HTML en templates, sin ".html"
    }
}

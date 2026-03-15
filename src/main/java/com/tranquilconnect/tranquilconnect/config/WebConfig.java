package com.tranquilconnect.tranquilconnect.config; // Ajusta a tu paquete

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Al acceder a /imagenes/**, Spring buscará físicamente en la carpeta uploads/
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:uploads/");
    }
}
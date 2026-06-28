package com.tranquilconnect.tranquilconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // NECESARIO
import org.springframework.boot.autoconfigure.domain.EntityScan; // NECESARIO

// NOTA: Asumí que tus paquetes modelo y repositorio están en 'com.tranquilconnect.tranquilconnect.model' y '.repository', 
// lo cual es la convención, pero si el error persiste, deberás cambiar las rutas de 'basePackages' por la ubicación real.

@SpringBootApplication
@ComponentScan(basePackages = {"com.tranquilconnect.tranquilconnect", "com.tranquilconnect.controladores"})

// INDICACIONES NUEVAS PARA JPA:
@EnableJpaRepositories(basePackages = "com.tranquilconnect.tranquilconnect.repository")
@EntityScan(basePackages = "com.tranquilconnect.tranquilconnect.model")
public class TranquilConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranquilConnectApplication.class, args);
    }

}

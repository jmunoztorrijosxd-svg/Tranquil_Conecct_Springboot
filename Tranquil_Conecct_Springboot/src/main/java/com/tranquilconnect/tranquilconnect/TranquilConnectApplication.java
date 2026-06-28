package com.tranquilconnect.tranquilconnect;

import com.tranquilconnect.tranquilconnect.model.Usuario; // Ajusta según tu paquete
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; // Ajusta según tu paquete
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tranquilconnect.tranquilconnect", "com.tranquilconnect.controladores"})
@EnableJpaRepositories(basePackages = "com.tranquilconnect.tranquilconnect.repository")
@EntityScan(basePackages = "com.tranquilconnect.tranquilconnect.model")
public class TranquilConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranquilConnectApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Cambia 'admin@tranquilconnect.com' por el correo que quieras
            if (usuarioRepository.findByEmail("admin@tranquilconnect.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setEmail("jmunoztorrijosxd@gmail.com");
                // IMPORTANTE: Asegúrate de tener un PasswordEncoder configurado en tu proyecto
                admin.setPassword(passwordEncoder.encode("12345678")); 
                admin.setRole("ROLE_ADMIN");
                
                usuarioRepository.save(admin);
                System.out.println("✅ Administrador creado exitosamente en la base de datos.");
            }
        };
    }
}
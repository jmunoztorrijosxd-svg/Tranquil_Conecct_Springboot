package com.tranquilconnect.tranquilconnect.service;

import com.tranquilconnect.tranquilconnect.model.Usuario; 
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método que usa Spring Security para buscar el usuario y sus credenciales
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // 1. Obtener el rol del usuario (ej: "ADMIN" o "USUARIO")
        String rol = usuario.getRol();
        
        // 2. Crear la autoridad de Spring Security (ej: ROLE_ADMIN o ROLE_USUARIO)
        // Spring Security requiere que los roles tengan el prefijo "ROLE_".
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
             new SimpleGrantedAuthority("ROLE_" + rol) // Concatena el prefijo
        );

        // 3. Devolver el objeto UserDetails con las autoridades correctas
        return new User(
                usuario.getCorreo(), 
                usuario.getPassword(), // Asegúrate que esta sea la contraseña en texto plano si usas NoOpPasswordEncoder
                authorities // Usa la lista de autoridades dinámica
        );
    }
}
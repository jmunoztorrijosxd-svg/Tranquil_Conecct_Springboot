// Archivo: com.tranquilconnect.tranquilconnect.service.UsuarioService.java

package com.tranquilconnect.tranquilconnect.service;

import com.tranquilconnect.tranquilconnect.model.Usuario;
import java.util.List;

public interface UsuarioService {
    // Este método es crucial para el filtro multicriterio.
    List<Usuario> buscarUsuarios(String keyword, String rol, String genero);
    
    // Otros métodos de tu CRUD si usas el Service (Recomendado)
    // Usuario save(Usuario usuario);
    // ...
}
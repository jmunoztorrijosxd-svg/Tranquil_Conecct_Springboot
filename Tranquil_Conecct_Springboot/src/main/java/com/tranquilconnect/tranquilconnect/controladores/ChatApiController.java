package com.tranquilconnect.tranquilconnect.controladores;

import com.tranquilconnect.tranquilconnect.model.MensajeChat;
import com.tranquilconnect.tranquilconnect.service.MensajeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // Esto permite que otros puertos (como el 8000 de Python) consulten esta API
public class ChatApiController {

    @Autowired
    private MensajeChatService mensajeChatService;

    /**
     * Obtiene todos los mensajes de un grupo específico.
     * URL de prueba: http://localhost:8080/api/chat/historial/1
     */
    @GetMapping("/historial/{idGrupo}")
    public ResponseEntity<List<MensajeChat>> obtenerHistorial(@PathVariable Integer idGrupo) {
        List<MensajeChat> mensajes = mensajeChatService.obtenerHistorialPorGrupo(idGrupo);
        
        if (mensajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(mensajes);
    }

    /**
     * Endpoint opcional para guardar mensajes directamente desde Java
     */
    @PostMapping("/enviar")
    public ResponseEntity<MensajeChat> guardarMensajeDesdeJava(@RequestBody MensajeChat nuevoMensaje) {
        MensajeChat guardado = mensajeChatService.guardarMensaje(nuevoMensaje);
        return ResponseEntity.ok(guardado);
    }
}
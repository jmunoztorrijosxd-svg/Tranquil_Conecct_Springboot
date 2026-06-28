package com.tranquilconnect.tranquilconnect.service;

import com.tranquilconnect.tranquilconnect.model.MensajeChat;
import com.tranquilconnect.tranquilconnect.repository.MensajeChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MensajeChatService {

    @Autowired
    private MensajeChatRepository mensajeRepository;

    // Obtener historial de un grupo (lo que escribió Python)
    public List<MensajeChat> obtenerHistorialPorGrupo(Integer idGrupo) {
        return mensajeRepository.findByIdGrupoOrderByFechaEnvioAsc(idGrupo);
    }

    // Guardar un mensaje desde Java (si fuera necesario)
    public MensajeChat guardarMensaje(MensajeChat mensaje) {
        return mensajeRepository.save(mensaje);
    }
}
package com.tranquilconnect.tranquilconnect.controladores;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import com.mercadopago.exceptions.MPApiException;
import org.springframework.security.core.Authentication;
import com.tranquilconnect.tranquilconnect.model.Usuario;
import com.tranquilconnect.tranquilconnect.model.Cita;
import java.time.LocalDate;
import com.tranquilconnect.tranquilconnect.repository.UsuarioRepository;
import com.tranquilconnect.tranquilconnect.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class PagoController {

    // Inyecta la variable desde application.properties
    @Value("${BASE_URL}")
    private String baseUrl;

    private final UsuarioRepository usuarioRepository;
    private final CitaRepository citaRepository;

    public PagoController(UsuarioRepository usuarioRepository, CitaRepository citaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.citaRepository = citaRepository;
    }

    // El @CrossOrigin permite que servidores externos como Mercado Pago envíen POSTs aquí
    @CrossOrigin(origins = "*") 
    @PostMapping("/notificaciones-mp")
    public ResponseEntity<String> recibirNotificacion(@RequestParam Map<String, String> allParams) {
        String topic = allParams.get("topic");
        String id = allParams.get("id");

        if ("payment".equals(topic) && id != null) {
            try {
                MercadoPagoConfig.setAccessToken("APP_USR-8360299182309574-062413-c7dffaa1811911b87093054a9f79431d-3494430433");
                Payment payment = new PaymentClient().get(Long.parseLong(id));

                if ("approved".equals(payment.getStatus())) {
    String externalRef = payment.getExternalReference();
    if (externalRef != null) {
        citaRepository.findById(Long.parseLong(externalRef)).ifPresent(cita -> {
            // ELIMINA O COMENTA ESTAS LÍNEAS:
            // cita.setEstado("APROBADA");
            // citaRepository.save(cita);
            
            // OPCIONAL: Puedes cambiar el estado a "PAGADA" para diferenciarlo
            cita.setEstado("PAGADA"); 
            citaRepository.save(cita);
        });
    }
}
            } catch (Exception e) {
                System.err.println("Error en Webhook: " + e.getMessage());
            }
        }
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/pago")
    public String irAPago(@RequestParam Long psicologoId, @RequestParam String fecha, HttpSession session) {
        session.setAttribute("psicologoId", psicologoId);
        session.setAttribute("fecha", fecha);
        return "pago";
    }

    @PostMapping("/confirmar-pago")
    public String procesarPago(HttpSession session, Authentication auth) throws Exception {
        try {
            Long psicologoId = (Long) session.getAttribute("psicologoId");
            String fecha = (String) session.getAttribute("fecha");
            String correo = auth.getName();
            
            Usuario paciente = usuarioRepository.findByCorreo(correo).orElseThrow();
            Usuario psicologo = usuarioRepository.findById(psicologoId).orElseThrow();
            
            Cita cita = new Cita();
            cita.setPaciente(paciente);
            cita.setPsicologo(psicologo);
            cita.setFecha(LocalDate.parse(fecha));
            cita.setEstado("PENDIENTE");
            cita = citaRepository.save(cita);

            session.setAttribute("citaIdEnProceso", cita.getId());

            MercadoPagoConfig.setAccessToken("APP_USR-8360299182309574-062413-c7dffaa1811911b87093054a9f79431d-3494430433");

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Cita psicológica")
                    .quantity(1)
                    .currencyId("COP")
                    .unitPrice(new BigDecimal("50000"))
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(Collections.singletonList(item))
            .externalReference(cita.getId().toString())
            .notificationUrl(baseUrl + "/notificaciones-mp")
            .autoReturn("approved")
            .backUrls(PreferenceBackUrlsRequest.builder()
                .success(baseUrl + "/pago-exitoso")
                .failure(baseUrl + "/pago-fallido")
                .pending(baseUrl + "/pago-pendiente")
                .build())
            .build();

            Preference preference = new PreferenceClient().create(preferenceRequest);
            return "redirect:" + preference.getInitPoint();
            
        } catch (MPApiException e) {
            System.err.println("Error en Mercado Pago: " + e.getApiResponse().getContent());
            return "redirect:/agendar?error";
        }
    }

    @GetMapping("/pago-exitoso")
public String pagoExitoso(
        @RequestParam Map<String,String> params,
        Authentication auth) {

    System.out.println("=================================");
    System.out.println("Usuario autenticado: " +
            (auth != null ? auth.getName() : "NULL"));
    System.out.println("PARAMS MP = " + params);
    System.out.println("=================================");

    return "redirect:/agendar?pagado";
}

    @GetMapping("/pago-fallido")
    public String pagoFallido(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", "El pago no pudo completarse.");
        return "redirect:/pago";
    }

    @GetMapping("/pago-pendiente")
    public String pagoPendiente(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensaje", "Tu pago está pendiente.");
        return "redirect:/dashboard-paciente";
    }
}
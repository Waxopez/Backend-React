package cl.duoc.backend_adopcion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.backend_adopcion.model.Solicitud;
import cl.duoc.backend_adopcion.repository.SolicitudRepository;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "http://localhost:5173")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody Solicitud solicitud) {
        try {
            if (solicitud == null) {
                return ResponseEntity.badRequest().body("La solicitud no puede ser nula");
            }
            Solicitud nueva = solicitudRepository.save(solicitud);
            return ResponseEntity.ok(nueva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar la solicitud");
        }
    }
}
package cl.duoc.backend_adopcion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.backend_adopcion.service.HuachitosService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Permiso para React
public class MascotaController {

    @Autowired
    private HuachitosService HuachitosService;

    @GetMapping("/mascotas")
    public ResponseEntity<?> obtenerMascotas() {
        Object respuesta = HuachitosService.getMascotasHuachitos();
        return ResponseEntity.ok(respuesta);
    }
}
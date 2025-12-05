package cl.duoc.backend_adopcion.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; // Importamos el servicio nuevo
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.backend_adopcion.model.Usuario;
import cl.duoc.backend_adopcion.repository.UsuarioRepository;
import cl.duoc.backend_adopcion.security.JwtService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService; // Inyección de dependencia

    // REGISTRO (Se mantiene igual)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole("USER");
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // LOGIN (Actualizado con JWT)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginData) {
        Usuario user = usuarioRepository.findByEmail(loginData.getEmail()).orElse(null);

        if (user != null && passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
            
            // 1. Generamos el Token
            String token = jwtService.generateToken(user.getEmail());
            
            // 2. Preparamos la respuesta (Token + Datos del usuario)
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", user); // Enviamos datos para el Perfil
            
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }
}
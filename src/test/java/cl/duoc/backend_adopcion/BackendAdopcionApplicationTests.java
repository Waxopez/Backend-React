package cl.duoc.backend_adopcion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // Simula el servidor web sin levantarlo completo
class BackendAdopcionApplicationTests {

	@Autowired
	private MockMvc mockMvc; // Herramienta para lanzar peticiones falsas

	@Test
	void contextLoads() {
		// Este test solo verifica que Spring Boot sea capaz de arrancar sin errores
	}

	@SuppressWarnings("null")
	@Test
	void intentarLoginSinDatos_DeberiaFallar() throws Exception {
		// PRUEBA DE INTEGRACIÓN:
		// Intentamos hacer POST a /auth/login sin enviar contraseña.
		// El sistema debería responder con error 400 (Bad Request).
		
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")) // Enviamos JSON vacío
				.andExpect(status().isBadRequest()); // Esperamos un error 400
	}
}
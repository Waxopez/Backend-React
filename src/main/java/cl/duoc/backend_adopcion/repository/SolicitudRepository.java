package cl.duoc.backend_adopcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.backend_adopcion.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    // No necesitamos métodos especiales por ahora, con save() nos basta.
}
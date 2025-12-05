package cl.duoc.backend_adopcion.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "solicitudes")
@Data
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreSolicitante;
    private String email;
    private String telefono;
    private String nombreMascota; // Guardamos el nombre del perro seleccionado
    
    private String edadSolicitante; // "25 años / familia de 4"
    private String preferencia; // "inmediata", "semana", etc.
    
    @Column(length = 1000)
    private String mensaje;

    private LocalDateTime fechaCreacion;

    @PrePersist
    public void asignarFecha() {
        this.fechaCreacion = LocalDateTime.now();
    }
}
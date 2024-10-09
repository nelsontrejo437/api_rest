package org.seguridad.api_rest.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RegistroConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100000)
    private String consulta;

    @Column(length = 100000)
    private String respuesta;

    @Column(name = "fecha_consulta", length = 100000)
    private LocalDateTime fecha_consulta;

    private String username;
}

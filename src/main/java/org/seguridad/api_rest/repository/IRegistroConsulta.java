package org.seguridad.api_rest.repository;

import java.util.List;

import org.seguridad.api_rest.models.RegistroConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroConsulta extends JpaRepository<RegistroConsulta, Long> {
    List<RegistroConsulta> findByUsername(String username);
}

package org.seguridad.api_rest.repository;

import org.seguridad.api_rest.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUsuarios extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByUsername(String username);
}

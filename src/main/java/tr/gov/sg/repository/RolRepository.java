package tr.gov.sg.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.gov.sg.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, UUID> {

}

package tr.gov.sg.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.gov.sg.entity.Raf;

public interface RafRepository extends JpaRepository<Raf, UUID> {

}

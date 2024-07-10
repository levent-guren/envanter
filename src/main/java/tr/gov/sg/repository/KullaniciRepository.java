package tr.gov.sg.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.gov.sg.entity.Kullanici;

public interface KullaniciRepository extends JpaRepository<Kullanici, UUID> {
	public Optional<Kullanici> findByEmailIgnoreCase(String email);
}

package tr.gov.sg.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.gov.sg.entity.Kategori;

public interface KategoriRepository extends JpaRepository<Kategori, UUID> {

}

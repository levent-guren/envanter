package tr.gov.sg.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "urunler")
@EqualsAndHashCode(exclude = "urunler")
public class Kategori {
	@Id
	private UUID id;
	@Column(length = 30)
	private String adi;
	@OneToMany(mappedBy = "kategori")
	private List<Urun> urunler;

	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID();
	}
}

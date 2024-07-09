package tr.gov.sg.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = { "kategori", "raflar", "islemler" })
@EqualsAndHashCode(exclude = { "kategori", "raflar", "islemler" })
public class Urun {
	@Id
	private UUID id;
	@Column(length = 30)
	private String adi;
	@Column(precision = 11, scale = 2)
	private BigDecimal fiyat;
	@ManyToOne
	private Kategori kategori;
	@OneToMany(mappedBy = "urun")
	private List<Raf> raflar;
	@OneToMany(mappedBy = "urun")
	private List<Islem> islemler;

	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID();
	}

}

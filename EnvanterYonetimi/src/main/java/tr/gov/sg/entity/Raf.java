package tr.gov.sg.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "urun")
@EqualsAndHashCode(exclude = "urun")
public class Raf {
	@Id
	private UUID id;
	@Column(precision = 5)
	private int sira;
	@ManyToOne
	private Urun urun;
	@Column(precision = 5, nullable = false)
	private int urunAdedi;

	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID();
	}

}

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
public class Islem {
	@Id
	private UUID id;
	@Column(precision = 2, name = "islem_tipi")
	private IslemTipi islemTipi;
	@ManyToOne
	private Urun urun;
	private int miktar;

	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID();
	}

}

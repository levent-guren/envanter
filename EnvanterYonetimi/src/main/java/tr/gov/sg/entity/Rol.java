package tr.gov.sg.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "kullanicilar")
@EqualsAndHashCode(exclude = "kullanicilar")
public class Rol {
	@Id
	private UUID id;
	@Column(length = 30, nullable = false)
	private String adi;
	@ManyToMany(mappedBy = "roller")
	private List<Kullanici> kullanicilar;

	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID();
	}

}

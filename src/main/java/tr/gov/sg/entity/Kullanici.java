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
@ToString(exclude = "roller")
@EqualsAndHashCode(exclude = "roller")
public class Kullanici {
	@Id
	private UUID id;
	@Column(length = 30)
	private String adi;
	@Column(length = 30)
	private String soyadi;
	@Column(length = 100)
	private String sifre;
	@Column(length = 30, unique = true)
	private String email;
	@ManyToMany
	private List<Rol> roller;

	@PrePersist
	public void generateId() {
		this.id = UUID.randomUUID();
	}

}

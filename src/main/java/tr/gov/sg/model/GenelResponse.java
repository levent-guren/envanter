package tr.gov.sg.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenelResponse {
	private int kod;
	private String mesaj;
	private Object parametre;

	public GenelResponse(int kod, String mesaj, Object parametre) {
		this.kod = kod;
		this.mesaj = mesaj;
		this.parametre = parametre;
	}

	public GenelResponse(int kod, String mesaj) {
		this.kod = kod;
		this.mesaj = mesaj;
	}

	public GenelResponse(String mesaj) {
		this.mesaj = mesaj;
	}

}

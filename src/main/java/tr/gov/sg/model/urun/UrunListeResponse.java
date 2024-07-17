package tr.gov.sg.model.urun;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UrunListeResponse {
	private String uuid;
	private String adi;
	private BigDecimal fiyat;
	private String kategoriAdi;
}

package tr.gov.sg.model;

import lombok.Data;

@Data
public class GirisRequest {
	private String email;
	private String sifre;
}

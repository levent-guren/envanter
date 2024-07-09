package tr.gov.sg.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.gov.sg.model.GirisRequest;
import tr.gov.sg.model.GirisResponse;

@RestController
public class GirisController {
	@PostMapping("/giris")
	public GirisResponse girisYap(GirisRequest girisRequest) {
		GirisResponse response = new GirisResponse();
		response.setToken("token bilgisi burada olacak");
		return response;
	}
}

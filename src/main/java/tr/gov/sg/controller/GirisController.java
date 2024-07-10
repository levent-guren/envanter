package tr.gov.sg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.gov.sg.model.GirisRequest;
import tr.gov.sg.model.GirisResponse;
import tr.gov.sg.service.GirisService;

@RestController
public class GirisController {
	@Autowired
	private GirisService girisService;

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/giris")
	public ResponseEntity<GirisResponse> girisYap(@RequestBody GirisRequest girisRequest) {
		try {
			String token = girisService.girisYap(girisRequest.getEmail(), girisRequest.getSifre());
			GirisResponse response = new GirisResponse();
			response.setToken(token);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// Giriş başarısız.
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
}

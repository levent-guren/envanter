package tr.gov.sg.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PostMapping("/test")
	public String test() {
		return "başarılı";
	}

	@PostMapping("/testAdmin")
	public String testAdmin() {
		return "başarılı";
	}

	@PostMapping("/testSatis")
	public String testSatis() {
		return "başarılı";
	}
}

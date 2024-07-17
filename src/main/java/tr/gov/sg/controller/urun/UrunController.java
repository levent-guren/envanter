package tr.gov.sg.controller.urun;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tr.gov.sg.entity.Urun;
import tr.gov.sg.model.GenelResponse;
import tr.gov.sg.model.urun.UrunKaydetRequest;
import tr.gov.sg.model.urun.UrunListeResponse;
import tr.gov.sg.model.urun.UrunSilRequest;
import tr.gov.sg.service.urun.UrunService;

@RestController
@RequestMapping("/urun")
public class UrunController {
	@Autowired
	private UrunService urunService;
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	@CrossOrigin(origins = { "http://localhost:4200" })
	public ResponseEntity<List<UrunListeResponse>> getUrunListesi() {
		try {
			List<Urun> urunler = urunService.getUrunler();
			List<UrunListeResponse> sonuc = urunler.stream().map(u -> {
				UrunListeResponse resp = mapper.map(u, UrunListeResponse.class);
				resp.setUuid(u.getId().toString());
//				UrunListeResponse resp = new UrunListeResponse();
//				resp.setAdi(u.getAdi());
//				resp.setFiyat(u.getFiyat());
//				resp.setKategoriAdi(u.getKategori().getAdi());
//				resp.setUuid(u.getId().toString());
				return resp;
			}).collect(Collectors.toList());
			return ResponseEntity.ok(sonuc);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping
	@CrossOrigin(origins = { "http://localhost:4200" })
	public ResponseEntity<GenelResponse> urunKaydet(@RequestBody UrunKaydetRequest kaydedilecekUrun) {
		try {
			Urun urun = mapper.map(kaydedilecekUrun, Urun.class);
			urun.setId(UUID.fromString(kaydedilecekUrun.getUuid()));
			Urun yaratilanUrun = urunService.urunKaydet(urun, kaydedilecekUrun.getKategoriId());
			return ResponseEntity.ok(new GenelResponse(0, "Ürün kaydedilmiştir.", yaratilanUrun.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GenelResponse(1, "Ürün kaydedilememiştir."));
		}
	}

	@DeleteMapping
	@CrossOrigin(origins = { "http://localhost:4200" })
	public ResponseEntity<GenelResponse> urunSil(@RequestBody UrunSilRequest urun) {
		try {
			urunService.urunSil(UUID.fromString(urun.getUuid()));
			return ResponseEntity.ok(new GenelResponse("Ürün silinmiştir."));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new GenelResponse(1, "Ürün silinememiştir."));
		}
	}

	void test() {
		Urun urun = urunService.getUrunById(UUID.fromString("123213-123-1231-2312"));
		System.out.println(urun.getAdi());
		System.out.println(urun.getFiyat());
		System.out.println(urun.getKategori());
	}
}

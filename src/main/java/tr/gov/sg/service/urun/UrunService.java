package tr.gov.sg.service.urun;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tr.gov.sg.entity.Kategori;
import tr.gov.sg.entity.Urun;
import tr.gov.sg.repository.KategoriRepository;
import tr.gov.sg.repository.UrunRepository;

@Service
public class UrunService {
	@Autowired
	private UrunRepository urunRepository;
	@Autowired
	private KategoriRepository kategoriRepository;

	@Transactional
	public List<Urun> getUrunler() {
		List<Urun> urunler = urunRepository.findAll();
		for (int i = 0; i < urunler.size(); i++) {
			// ürünlerin kategorileri de okunup içine konuyor.
			urunler.get(i).getKategori();
		}
		return urunler;
	}

	@Transactional
	public Urun urunKaydet(Urun urun) {
		return urunRepository.save(urun);
	}

	@Transactional
	public void urunSil(UUID urunId) {
		Urun urun = urunRepository.findById(urunId).orElseThrow();
		urunRepository.delete(urun);
	}

	public Urun urunKaydet(Urun urun, String kategoriId) {
		Kategori kategori = kategoriRepository.findById(UUID.fromString(kategoriId)).orElseThrow();
		urun.setKategori(kategori);
		return urunKaydet(urun);
	}

	public Urun getUrunById(UUID uuid) {
		return urunRepository.findById(uuid).orElseThrow();
	}
}

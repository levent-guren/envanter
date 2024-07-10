package tr.gov.sg.service;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.gov.sg.entity.Kullanici;
import tr.gov.sg.repository.KullaniciRepository;
import tr.gov.sg.util.Security;

@Service
public class GirisService {
	@Autowired
	private KullaniciRepository kullaniciRepository;
	@Autowired
	private Security security;

	public String girisYap(String email, String password) throws LoginException {
		// Kullanıcı bilgisi email'e göre veri tabanından okunuyor.
		Optional<Kullanici> oKullanici = kullaniciRepository.findByEmailIgnoreCase(email);
		// Kullanıcı bulunamadı ise Exception fırlatılıyor. (NoSuchElement)
		Kullanici kullanici = oKullanici.orElseThrow();
		if (kullanici.getSifre().equals(password)) {
			// Giriş başarılı ise token üretiliyor.
			return security.tokenUret(kullanici);
		}
		// şifre hatalı
		throw new LoginException();
	}
}

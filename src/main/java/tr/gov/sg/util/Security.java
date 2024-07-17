package tr.gov.sg.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import tr.gov.sg.entity.Kullanici;
import tr.gov.sg.entity.Rol;

@SuppressWarnings("unused")
@Component
public class Security implements CommandLineRunner {
	@Value("${SECRET_KEY}")
	private String SECRET_KEY;

	public String tokenUret(Kullanici kullanici) {
		JwtBuilder builder = Jwts.builder();
		List<Rol> roller = kullanici.getRoller();
		String roles = roller.stream().map(Rol::getAdi).reduce((a, b) -> a + "," + b).orElse("");
		Map<String, Object> customKeys = new HashMap<>();
		customKeys.put("roller", roles);
		// customKeys.put("kullaniciId", kullanici.getId().toString());
		customKeys.put("kullaniciAdi", kullanici.getAdi());
		builder = builder.claims(customKeys);
		Instant tarih = Instant.now().plus(1, ChronoUnit.DAYS);
		builder = builder.subject("login").issuedAt(new Date()).expiration(Date.from(tarih));
		return builder.signWith(getKey()).compact();
	}

	public Claims validateTokenAndGetClaims(String token) {
		JwtParser parser = Jwts.parser().verifyWith(getKey()).build();
		// parseSignClaims metodu hem SECRET_KEY kontrolünü yapıyor,
		// hem de token'ın zamanı geçmiş mi kontrolünü yapıyor.
		// Herhangi bir hata durumunda da exception fırlatıyor.

		return parser.parseSignedClaims(token).getPayload();
	}

	private SecretKey getKey() {
		byte[] decoded = Base64.getDecoder().decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decoded);
	}

	public static void main(String[] args) {
		// keyUret();
		// keyTest();
		streamBirlestirmeTesti();
	}

	private static void streamBirlestirmeTesti() {
		List<Rol> roller = new ArrayList<Rol>();
		Rol rol = new Rol();
		rol.setAdi("rol1");
		roller.add(rol);
		rol = new Rol();
		rol.setAdi("rol2");
		roller.add(rol);
		rol = new Rol();
		rol.setAdi("rol3");
		roller.add(rol);
		String roles = roller.stream().map(Rol::getAdi).reduce((a, b) -> a + "," + b).orElse("");
		System.out.println(roles);
	}

	private static void keyTest() {
		SecretKey key = Jwts.SIG.HS512.key().build();
		String token = tokenUretTest(key);
		System.out.println(token);
		Claims claims = verifyToken(token, key);
		System.out.println(claims.get("rol"));
		System.out.println(claims.get("id"));
	}

	private static Claims verifyToken(String token, SecretKey key) {
		JwtParser builder = Jwts.parser().verifyWith(key).build();
		return builder.parseSignedClaims(token).getPayload();

	}

	private static String tokenUretTest(SecretKey key) {
		String token = Jwts.builder().claim("rol", "ADMIN").claim("id", "1").expiration(new Date()).signWith(key)
				.compact();
		return token;
	}

	private static void keyUret() {
		SecretKey key = Jwts.SIG.HS512.key().build();
		String str = Encoders.BASE64.encode(key.getEncoded());
		System.out.println(str);
	}

	@Override
	public void run(String... args) throws Exception {
		// System.out.println("SECRET_KEY:" + SECRET_KEY);
	}

}

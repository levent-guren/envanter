package tr.gov.sg.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tr.gov.sg.util.Security;

@Component
public class JWTSecurityFilter extends OncePerRequestFilter {
	@Autowired
	private Security security;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// token var mı?
		try {
			String token = request.getHeader("Authorization");
			if (token != null && token.startsWith("Bearer ")) {
				// Token var.
				// token geçerli mi?
				token = token.substring(7);
				Claims claims = security.validateTokenAndGetClaims(token);
				// elimizde token'ın payload(body) kısmı var.
				if ("login".equals(claims.getSubject())) {
					// String kullaniciId = (String) claims.get("kullaniciId");
					String kullaniciAdi = (String) claims.get("kullaniciAdi");
					String rollerStr = (String) claims.get("roller");
					String[] rollerArr = rollerStr.split(",");
					// List<SimpleGrantedAuthority> rolListesi = Arrays.stream(rollerArr)
					// .map(rolAdi -> new
					// SimpleGrantedAuthority("ROLE_" + rolAdi)).collect(Collectors.toList());
					List<SimpleGrantedAuthority> rolListesi = new ArrayList<SimpleGrantedAuthority>();
					for (int i = 0; i < rollerArr.length; i++) {
						SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + rollerArr[i]);
						rolListesi.add(auth);
					}

					UsernamePasswordAuthenticationToken authentication =
							// şifre kontrolü spring tarafından yapılmayacağından
							// şifre olarak boş string veriyoruz.
							new UsernamePasswordAuthenticationToken(kullaniciAdi, "", rolListesi);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			// hata varsa bir şey yapma.
		}
		// token yoksa bir şey yapmıyoruz.
		filterChain.doFilter(request, response);
	}

}

package tr.gov.sg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tr.gov.sg.filter.JWTSecurityFilter;

@Configuration
public class SecurityConfig {
	@Autowired
	private JWTSecurityFilter jwtSecurityFilter;

	@Bean
	AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		return authenticationManagerBuilder.build();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request -> 
					// giris url'i her zaman çağırılabilsin.
					request.requestMatchers("/giris").permitAll()
					.requestMatchers("/testAdmin").hasRole("ADMIN")
					.requestMatchers("/testSatis").hasRole("SATIS")
					// bunun dışındaki tüm url'ler için login olunma şartı aransın
					.anyRequest().authenticated()
				)
				// Her request için yeni bir session yaratılsın.
				// Response döndüğünde ise session saklanmayıp silinsin.
				.sessionManagement(sessionCreationPolicy -> 
				  sessionCreationPolicy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		// @formatter:on
	}
}

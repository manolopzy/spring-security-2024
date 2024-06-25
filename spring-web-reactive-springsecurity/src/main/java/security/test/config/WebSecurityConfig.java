package security.test.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * CORS must be processed before Spring Security comes into action since
 * preflight requests will not contain cookies and Spring Security will reject
 * the request as it will determine that the user is not authenticated.
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.cors(cors -> cors.disable()).securityMatcher(new PathPatternParserServerWebExchangeMatcher("/**"))
				.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated()).httpBasic();
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfiguration() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.applyPermitDefaultValues();
		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedMethod("GET");
		corsConfig.addAllowedMethod("PATCH");
		corsConfig.addAllowedMethod("POST");
		corsConfig.addAllowedMethod("OPTIONS");
		corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
		corsConfig.setExposedHeaders(Arrays.asList("X-Get-Header"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		return new CorsWebFilter(corsConfiguration());
	}
}

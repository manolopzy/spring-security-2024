package security.test.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class FluxwebCorsConfig {
	/**
	 * Supposing that "http://localhost:3000" is our static contents server address
	 * This is an preferable alternative of configuring cors instead of 
	 * using @{link WebFluxConfigurer} overwriting its "addCorsMappings" method
	 * @return
	 */
	@Bean
	public CorsWebFilter corsWebFilter() {
	    CorsConfiguration corsConfig = new CorsConfiguration();
	    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	    corsConfig.setMaxAge(3600L);
	    corsConfig.addAllowedMethod("*");
	    corsConfig.addAllowedHeader("Requestor-Type");
	    corsConfig.addExposedHeader("X-Get-Header");

	    UrlBasedCorsConfigurationSource source =
	        new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfig);

	    return new CorsWebFilter(source);
	}
}

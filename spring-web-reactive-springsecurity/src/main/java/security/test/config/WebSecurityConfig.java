package security.test.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

/**
 * CORS must be processed before Spring Security comes into action since
 * preflight requests will not contain cookies and Spring Security will reject
 * the request as it will determine that the user is not authenticated.
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    /**
     * Disable spring security cors default configuration, because it runs before 
     * any thing else, so if we want create a {@link CorsWebFilter} which is 
     * the preferable way for Web Flux cors configuration ...
     * 
     * 
     * "authorizeExchange" configures only the way of authorization
     * "httpBasic" while "httpBasic" configures the allowed http methods, 
     * headers, and by default it permits all types of methods and headers.
     * 
     * @param http
     * @return
     */
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    	http.httpBasic(Customizer.withDefaults());
    	/**
    	 * On the contrary, this configuration requires that all 
    	 * requests are anthenticated
    	 */
    	//Failed to load resource: the server responded with a status of 401 (Unauthorized)
//    	http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/**"))
//		.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated());
		/**
		 * This configuration simply permits all requests to any endpoints without 
		 * any authentication required.
		 */
    	http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/**"))
				.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll());
		return http.build();
	}

    /**
     * When {@link @EnableWebFluxSecurity} is used, the default cors configuration 
     * will be overwritten by Spring webflux security
     * @return
     */
	@Bean
	public CorsConfigurationSource corsConfiguration() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.applyPermitDefaultValues();
		//corsConfig.setAllowCredentials(true);
//		corsConfig.addAllowedMethod("GET");
//		corsConfig.addAllowedMethod("DELETE");
//		corsConfig.addAllowedMethod("PATCH");
//		corsConfig.addAllowedMethod("POST");
//		corsConfig.addAllowedMethod("OPTIONS");
		//corsConfig.setAllowedMethods(Arrays.asList("POST"));
		corsConfig.setAllowedMethods(Arrays.asList("GET", "DELETE", "POST"));
		corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
//		corsConfig.setExposedHeaders(Arrays.asList("X-Get-Header"));
		corsConfig.setAllowedHeaders(Arrays.asList("*"));
		corsConfig.setExposedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public CorsWebFilter corsWebFilter() {
		return new CorsWebFilter(corsConfiguration());
	}
	
	@Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("username")
                .password("password")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
//	private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
//	  private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
//	  private static final String ALLOWED_ORIGIN = "*";
//	  private static final String MAX_AGE = "3600";
//
//	  @Bean
//	  public WebFilter corsFilter() {
//	    return (ServerWebExchange ctx, WebFilterChain chain) -> {
//	      ServerHttpRequest request = ctx.getRequest();
//	      if (CorsUtils.isCorsRequest(request)) {
//	        ServerHttpResponse response = ctx.getResponse();
//	        HttpHeaders headers = response.getHeaders();
//	        headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
//	        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
//	        headers.add("Access-Control-Max-Age", MAX_AGE);
//	        headers.add("Access-Control-Allow-Headers",ALLOWED_HEADERS);
//	        if (request.getMethod() == HttpMethod.OPTIONS) {
//	          response.setStatusCode(HttpStatus.OK);
//	          return Mono.empty();
//	        }
//	      }
//	      return chain.filter(ctx);
//	    };
//	  }
}

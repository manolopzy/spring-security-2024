package security.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * whenever one of the request's url protocal, domain name or port is different 
 * from the current page's url, it will be considered as a cross origin request
 * 
 * 
 * 
 * In this project, the front end is implemented by React, and the origin of 
 * the static contents is http://localhost:3000, in which by default it cannot 
 * have access to this server which has a different URL.
 * 
 * If the configuration did not contains "DELETE" method, then the front end 
 * would not be able to execute "delete" http requests. The error showed in the 
 * browser would be the following:
 * allowedMethods("GET", "POST", "PUT");
 * -------Error----
 * http://localhost:8081/users/404' from origin 
 * 'http://localhost:3000' has been blocked by CORS policy: 
 * Response to preflight request doesn't pass access control check: 
 * No 'Access-Control-Allow-Origin' header is present on the requested 
 * resource. If an opaque response serves your needs, set the request's 
 * mode to 'no-cors' to fetch the resource with CORS disabled.
 * 
 * With or without the {@link @EnableWebMvc} annotation, the cross origin resource 
 * sharing configuration works.
 * 
 * 
 * However we need to use @EnableWebFlux to enable support for reactive web application 
 * using the  Spring WebFlux framework.
 * @author tanku
 *
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
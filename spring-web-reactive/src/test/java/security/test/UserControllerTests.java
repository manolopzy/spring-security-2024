package security.test;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import security.test.controller.UserController;
import security.test.entity.User;
import security.test.repository.UserRepository;
import security.test.service.UserService;

public class UserControllerTests {

	// Flux.just(User.of("manolo", "manolo", "Spain"), User.of("manuel", "manuel",
	// "Spain"), User.of("David", "David", "Spain"))
	@Test
	public void getRecentUsers() {
		User[] users = { 
				User.of("manolo", "manolo", "Spain"), 
				User.of("manuel", "manuel", "Spain"),
				User.of("David", "David", "Spain") 
				};
		// Ctrl + 1 to generate a new local variable to receive the returned value from
		// a method call
		Flux<User> userFlux = Flux.just(users);

		final String urlString = "/users?recent";
		UserRepository userRepository = Mockito.mock(UserRepository.class);
		when(userRepository.findAll()).thenReturn(userFlux);
		
		UserService userService = Mockito.mock(UserService.class);
		when(userService.getAll()).thenReturn(userFlux);
		
		WebTestClient webTestClient = 
				WebTestClient.bindToController(new UserController(userService)).build();
		webTestClient
		.get()
		.uri(urlString)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$").isArray()
		.jsonPath("$").isNotEmpty()
		.jsonPath("$[0].username").isEqualTo(users[0].getUsername().toString())
		.jsonPath("$[0].password").isEqualTo(users[0].getPassword())
		.jsonPath("$[0].fullname").isEqualTo(users[0].getFullname())
		.jsonPath("$[3]").doesNotExist();
		
		
		webTestClient.get().uri(urlString)
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(User.class)
		.contains(Arrays.copyOf(users, 3));
	}
	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void shouldSaveATaco() {
//		User[] users = { 
//				User.of("manolo", "manolo", "Spain"), 
//				User.of("manuel", "manuel", "Spain"),
//				User.of("David", "David", "Spain") 
//				};
//		// Ctrl + 1 to generate a new local variable to receive the returned value from
//		// a method call
//		
//		UserService userService = Mockito.mock(UserService.class);
//		Mono<User> unsavedUserMono = 
//				Mono.just(User.of("manolo", "manolo", "Spain"));
//		User savedUser = User.of("manolo", "manolo", "Spain");
//		Mono<User> savedUserMono = 
//				Mono.just(savedUser);
//		when(userService.saveOrUpdate((Mono<User>) any(Mono.class))).thenReturn(savedUserMono);
//		
//		WebTestClient webTestClient = 
//				WebTestClient.bindToController(new UserController(userService)).build();
//		
//		webTestClient.post()
//		.uri("/users")
//		.contentType(MediaType.APPLICATION_JSON)
//		.body(unsavedUserMono, User.class)
//		.exchange()
//		.expectStatus().isCreated()
//		.expectBody(User.class)
//		.isEqualTo(savedUser);
//	}
}

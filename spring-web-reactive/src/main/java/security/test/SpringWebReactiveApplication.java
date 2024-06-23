package security.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import security.test.entity.User;
import security.test.repository.UserRepository;

@SpringBootApplication
public class SpringWebReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebReactiveApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@PostConstruct
	public void init() {
		System.out.println("--------init start--------");
		userRepository.saveAll(Flux.just(User.of("manolo", "manolo", "Spain"), User.of("manuel", "manuel", "Spain"), User.of("David", "David", "Spain"))).then();
		userRepository.save(User.of("ana", "ana", "Spain")).then();
		userRepository.saveAll(Mono.just(User.of("marta", "marta", "Spain"))).next();
		System.out.println("--------init end--------");
	}
}

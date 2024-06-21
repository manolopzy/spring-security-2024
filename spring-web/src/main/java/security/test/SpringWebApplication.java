package security.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import security.test.entity.User;
import security.test.repository.UserRepository;

@SpringBootApplication
public class SpringWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}
	@Autowired
	private UserRepository userRepository;
	@PostConstruct
	public void init() {
		userRepository.save(new User("manolo", "manolo", "Spain"));
		userRepository.save(new User("manuel", "manuel", "Spain"));
		userRepository.save(new User("David", "David", "Spain"));
	}
}

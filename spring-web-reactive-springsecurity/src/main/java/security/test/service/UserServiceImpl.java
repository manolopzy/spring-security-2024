package security.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import security.test.entity.User;
import security.test.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Flux<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Mono<User> getById(long userId) {
		Mono<User> user = userRepository.findById(userId);
		return user;
	}

	@Override
	public Mono<Void> delete(long userId) {
		return userRepository.deleteById(userId);
	}

	@Override
	public Mono<User> saveOrUpdate(Mono<User> user) {
		return userRepository.saveAll(user).next();
	}


}

package security.test.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import security.test.entity.User;

public interface UserService {

	public Flux<User> getAll();

	public Mono<User> getById(long userId);

	public Mono<Void> delete(long userId);

	public Mono<User> saveOrUpdate(Mono<User> user);

}

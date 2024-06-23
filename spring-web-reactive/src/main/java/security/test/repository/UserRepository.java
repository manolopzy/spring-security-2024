package security.test.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import security.test.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long>{

}

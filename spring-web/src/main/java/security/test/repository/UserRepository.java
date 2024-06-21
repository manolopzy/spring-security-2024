package security.test.repository;

import org.springframework.data.repository.CrudRepository;

import security.test.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

}

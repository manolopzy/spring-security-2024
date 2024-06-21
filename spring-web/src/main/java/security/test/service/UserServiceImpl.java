package security.test.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import security.test.entity.User;
import security.test.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User getById(long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		return userOptional.isPresent() ? userOptional.get() : null;
	}

	@Override
	public void delete(long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public User saveOrUpdate(User user) {
		return userRepository.save(user);
	}

}

package security.test.service;

import security.test.entity.User;

public interface UserService {

	public Iterable<User> getAll();

	public User getById(long userId);

	public void delete(long userId);

	public User saveOrUpdate(User user);
}

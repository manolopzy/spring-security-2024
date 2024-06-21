package security.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import security.test.entity.User;
import security.test.service.UserService;

/**
 * It seems like that the controller class level cross origin configuration 
 * will be overwritten by the global configuration
 */
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.DELETE, RequestMethod.GET})
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@GetMapping
	public Iterable<User> getAllUsers() {
		return userService.getAll();
	}

	@GetMapping("/{userId}")
	private User getUser(@PathVariable("userId") long userId) {
		return userService.getById(userId);
	}

	@DeleteMapping("/{userId}")
	private ResponseEntity<Long> deleteUser(@PathVariable("userId") long userId) {
		System.out.println("delete user");
		userService.delete(userId);
		return new ResponseEntity<>(userId,
			      HttpStatus.OK);
	}
	
	
	@PostMapping
	private long saveUser(@RequestBody User user) {
		System.out.println("post user");
		user = userService.saveOrUpdate(user);
		return user.getId();
	}

	@PutMapping("/user")
	private User update(@RequestBody User user) {
		user = userService.saveOrUpdate(user);
		return user;
	}
}

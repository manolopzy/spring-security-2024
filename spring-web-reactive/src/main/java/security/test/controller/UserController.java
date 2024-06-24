package security.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import security.test.entity.User;
import security.test.service.UserService;

/**
 * It seems like that the controller class level cross origin configuration 
 * will be overwritten by the global configuration
 */
@CrossOrigin(origins = "http://someorigin:3000", methods = {RequestMethod.DELETE, RequestMethod.GET})
@RestController
@RequestMapping("/users")
public class UserController {

	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public Flux<User> getAllUsers() {
		return userService.getAll();
	}

	@GetMapping("/{userId}")
	private Mono<User> getUser(@PathVariable("userId") long userId) {
		return userService.getById(userId);
	}

	@GetMapping(params="recent")
	public Flux<User> getRecentUsers() {
		return userService.getAll();
	}
	
	@DeleteMapping("/{userId}")
	private Mono<Void> deleteUser(@PathVariable("userId") long userId) {
		System.out.println("delete user");
		return userService.delete(userId);
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	private Mono<User> saveUser(@RequestBody Mono<User> user) {
		System.out.println("post user start");
		Mono<User> userMono =  userService.saveOrUpdate(user);
		System.out.println("post user end");
		return userMono;
	}

//	@PostMapping
//	private Mono<User> saveAllUsers(@RequestBody Mono<User> user) {
//		System.out.println("post user start");
//		Mono<User> userMono =  userService.saveOrUpdate(user);
//		System.out.println("post user end");
//		return userMono;
//	}
	
	@PutMapping("/user")
	private Mono<User> update(@RequestBody Mono<User> user) {
		return userService.saveOrUpdate(user);
	}
	
//	@PostMapping(consumes = "application/json")
//	@ResponseStatus(HttpStatus.CREATED)
//	public Mono<User> postTaco(@RequestBody Mono<User> userMono) {
//	return userService.saveOrUpdate(userMono);
//	}
}

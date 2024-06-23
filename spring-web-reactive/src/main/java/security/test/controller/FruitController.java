package security.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fruits")
public class FruitController {
	@CrossOrigin(origins = "http://localhost:30000")
	@GetMapping
	private Mono<List<String>> getFruits() {
		List<String> fruits = 
				new ArrayList<>();
		fruits.add("Banana");
		fruits.add("Happy");
		fruits.add("Watermelon");
		fruits.add("Apple");
		
		return Mono.just(fruits);
	}
}

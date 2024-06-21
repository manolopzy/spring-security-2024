package security.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
public class FruitController {

	@GetMapping
	private List<String> getFruits() {
		List<String> fruits = 
				new ArrayList<>();
		fruits.add("Banana");
		fruits.add("Happy");
		fruits.add("Watermelon");
		fruits.add("Apple");
		
		return fruits;
	}
}

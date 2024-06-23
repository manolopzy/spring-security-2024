package security.test.entity;

import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor

@Data

public class User {
	@Id
	private Long id;

	private String username;
	private String password;
	private String fullname;
	public static User of(String username, String password, String fullname) {
		User user = new User();
		user.username = username;
		user.password = password;
		user.fullname = fullname;
		return user;
	}
	
	
}

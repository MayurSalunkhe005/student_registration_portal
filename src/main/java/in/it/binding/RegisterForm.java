package in.it.binding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

	private String name;
	private String email;
	private String phno;
	private String country;
	private String state;
	private String city;
	private String pwd;
	private String pwdUpdated;
}

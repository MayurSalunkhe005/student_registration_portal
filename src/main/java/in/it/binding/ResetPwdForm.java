package in.it.binding;

import lombok.Data;

@Data
public class ResetPwdForm {

	private Integer userMasterId;
	private String email;
	private String newPassword;
	private String confirmPassword;
}

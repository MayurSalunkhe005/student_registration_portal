package in.it.service;

import java.util.Map;

import in.it.binding.LoginForm;
import in.it.binding.RegisterForm;
import in.it.binding.ResetPwdForm;
import in.it.dao.UserMaster;

public interface UserService {

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer cid);

	public Map<Integer, String> getCity(Integer sid);

	public UserMaster getUser(String email);

	public boolean saveUserMaster(RegisterForm regForm);

	public UserMaster logIn(LoginForm loginForm);

	public boolean resetPwd(ResetPwdForm rpf);


}

package in.it.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.it.binding.LoginForm;
import in.it.binding.RegisterForm;
import in.it.binding.ResetPwdForm;
import in.it.dao.UserMaster;
import in.it.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm loginForm, Model model) {

		UserMaster userMaster = userService.logIn(loginForm);

		if (userMaster == null) {
			model.addAttribute("errMsg", "Invalid Credential");
			return "login";
		}

		if (userMaster.getPwdUpdated().equals("NO")) {

			ResetPwdForm formObj = new ResetPwdForm();
			formObj.setUserMasterId(userMaster.getId());

			model.addAttribute("resetPwd", formObj);
			return "resetPwd";
		}

		return "redirect:dashboard";

	}

	@PostMapping("/updatePwd")
	public String updatePwd(@ModelAttribute("resetPwd") ResetPwdForm resetPwdForm, Model model) {

		if (!resetPwdForm.getNewPassword().equals(resetPwdForm.getConfirmPassword())) {
			model.addAttribute("errMsg", "Confirm password Should be Same As New Password");
			return "resetPwd";

		}
		boolean status = userService.resetPwd(resetPwdForm);

		if (status) {
			return "redirect:dashboard";
		}
		model.addAttribute("errMsg", "pwd update Failed");
		return "resetPwd";
	}

	@GetMapping("/registerPage")
	public String loadRegisterPage(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		
		  Map<Integer, String> countries = userService.getCountries();
		  model.addAttribute("countries", countries);
		 
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(RegisterForm regForm, Model model) {
		boolean saveUserMaster = userService.saveUserMaster(regForm);
		if (saveUserMaster) {
			model.addAttribute("successMsg", "Registration Success");
		} else {
			model.addAttribute("errMag", "Registratin Failed");
		}

		Map<Integer, String> countries = userService.getCountries();
		model.addAttribute("countries", countries);

		return "register";

	}

	@GetMapping("/getStates")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId") Integer cid) {
		return userService.getStates(cid);

	}

	@GetMapping("/getCities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") Integer sid) {
		return userService.getStates(sid);

	}

	public String resetPwd(Model model) {
		return "resetPwd";

	}

	public String logout(Model model) {
		return "login";

	}
}

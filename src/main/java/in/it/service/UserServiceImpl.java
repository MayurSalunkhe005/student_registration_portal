package in.it.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.it.binding.LoginForm;
import in.it.binding.RegisterForm;
import in.it.binding.ResetPwdForm;
import in.it.dao.CityMaster;
import in.it.dao.CountryMaster;
import in.it.dao.StateMaster;
import in.it.dao.UserMaster;
import in.it.repo.CityMasterRepo;
import in.it.repo.CountryMasterRepo;
import in.it.repo.StateMasterRepo;
import in.it.repo.UserMasterRepo;
import in.it.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryMasterRepo countryMasterRepo;

	@Autowired
	private StateMasterRepo stateMasterRepo;

	@Autowired
	private CityMasterRepo cityMasterRepo;

	@Autowired
	private UserMasterRepo userMasterRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> countries = new HashMap();

		List<CountryMaster> findAll = countryMasterRepo.findAll();

		findAll.forEach(c -> {
			countries.put(c.getCountryId(), c.getCountryName());
		});

		return countries;
	}

	@Override
	public Map<Integer, String> getStates(Integer cid) {

		Map<Integer, String> states = new HashMap();

		List<StateMaster> statesList = stateMasterRepo.findBycountryId(cid);

		statesList.forEach(s -> {
			states.put(s.getStateId(), s.getStateName());
		});

		return states;
	}

	@Override
	public Map<Integer, String> getCity(Integer sid) {

		Map<Integer, String> cities = new HashMap();

		List<CityMaster> cityList = cityMasterRepo.findByCityId(sid);

		cityList.forEach(city -> {
			cities.put(city.getCityId(), city.getCityName());
		});

		return cities;
	}

	@Override
	public UserMaster getUser(String email) {

		return userMasterRepo.findByEmail(email);
	}

	@Override
	public boolean saveUserMaster(RegisterForm regForm) {

		regForm.setPwd(generatedRandomPassword());
		regForm.setPwdUpdated("NO");

		UserMaster userEntity = new UserMaster();
		BeanUtils.copyProperties(regForm, userEntity);

		userMasterRepo.save(userEntity);

		String subject = "Your Account Is created - Ashok IT";
		String body = "Your pwd : " + regForm.getPwd();

		return emailUtils.sendEmail(subject, body, regForm.getEmail());
	}

	private String generatedRandomPassword() {

		String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ8123456789abcdefghijklmnopqrstuvwxyz";

		StringBuffer randomString = new StringBuffer(5);

		Random random = new Random();

		for (int i = 0; i < 5; i++) {

			int randomIndex = random.nextInt(alphanumericCharacters.length());
			char randomChar = alphanumericCharacters.charAt(randomIndex);
			randomString.append(randomChar);
		}
		return randomString.toString();
	}

	@Override
	public UserMaster logIn(LoginForm loginForm) {

		return userMasterRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPassword());

	}

	@Override
	public boolean resetPwd(ResetPwdForm rpf) {

		Optional<UserMaster> findById = userMasterRepo.findById(rpf.getUserMasterId());
		if (findById.isPresent()) {

			UserMaster userMaster = findById.get();
			userMaster.setPwd(rpf.getNewPassword());
			userMaster.setPwdUpdated("YES");
			userMasterRepo.save(userMaster);
			return true;
		}
		return false;
	}

}

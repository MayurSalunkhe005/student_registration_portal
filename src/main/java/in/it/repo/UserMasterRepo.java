package in.it.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.it.dao.UserMaster;

@Repository
public interface UserMasterRepo extends JpaRepository<UserMaster, Integer> {

	public UserMaster findByEmail(String email);

	public UserMaster findByEmailAndPwd(String email, String pwd);
}

package in.it.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.it.dao.StateMaster;

@Repository
public interface StateMasterRepo extends JpaRepository<StateMaster, Integer>{

	public List<StateMaster> findBycountryId(Integer cid);
}

package in.it.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.it.dao.CityMaster;

@Repository
public interface CityMasterRepo extends JpaRepository<CityMaster, Integer>{

	public List<CityMaster> findByCityId(Integer cityId);
}

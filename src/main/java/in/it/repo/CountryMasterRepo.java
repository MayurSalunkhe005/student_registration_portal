package in.it.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.it.dao.CountryMaster;

@Repository
public interface CountryMasterRepo extends JpaRepository<CountryMaster, Integer> {

}

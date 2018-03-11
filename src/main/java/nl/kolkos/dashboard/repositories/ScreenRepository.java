package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.Screen;

@Repository
public interface ScreenRepository extends CrudRepository<Screen, Long>{
	Screen findFirstByDashboardOrderByPositionAsc(Dashboard dashboard);
	
	List<Screen> findByDashboardOrderByPositionAsc(Dashboard dashboard);
	
	Screen findBySafeNameAndDashboard(String safeName, Dashboard dashboard);
	
	Screen findBySafeName(String safeName);
	
	List<Screen> findAllByOrderByNameAsc();
	
	Screen findFirstByDashboardOrderByPositionDesc(Dashboard dashboard);
	
	List<Screen> findAllByOrderByDashboardAscPositionAsc();
	
	Screen findById(Long id);
}

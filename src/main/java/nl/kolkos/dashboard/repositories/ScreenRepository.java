package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Screen;

@Repository
public interface ScreenRepository extends CrudRepository<Screen, Long>{
	Screen findFirstByDashboardOrderByLocationAsc(Dashboard dashboard);
	
	List<Screen> findByDashboardOrderByLocationAsc(Dashboard dashboard);
	
	Screen findBySafeNameAndDashboard(String safeName, Dashboard dashboard);
	
	Screen findBySafeName(String safeName);
	
	List<Screen> findAllByOrderByNameAsc();
	
	Screen findFirstByDashboardOrderByLocationDesc(Dashboard dashboard);
}

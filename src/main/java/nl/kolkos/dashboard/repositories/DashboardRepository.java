package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.Dashboard;


@Repository
public interface DashboardRepository extends CrudRepository<Dashboard, Long>{
	Dashboard findBySafeName(String safeName);
	
	Dashboard findById(Long id);
}

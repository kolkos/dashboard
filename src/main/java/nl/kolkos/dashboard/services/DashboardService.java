package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.repositories.DashboardRepository;

@Service
public class DashboardService {
	@Autowired
	private DashboardRepository dashboardRepository;
	
	public String createSafeName(String name) {
		name = name.replaceAll("\\s+", "_");
		name = name.replaceAll("[^A-Za-z0-9_]", "");
		
		return name;
	}
	
	public void save(Dashboard dashboard) {
		dashboard.setSafeName(this.createSafeName(dashboard.getName()));
		dashboardRepository.save(dashboard);
	}
	
	public Dashboard findBySafeName(String safeName) {
		return dashboardRepository.findBySafeName(safeName);
	}
}

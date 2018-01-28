package nl.kolkos.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.repositories.ScreenRepository;

@Service
public class ScreenService {
	@Autowired
	private ScreenRepository screenRepository;
	
	public String createSafeName(String name) {
		name = name.replaceAll("\\s+", "_");
		name = name.replaceAll("[^A-Za-z0-9_]", "");
		
		return name;
	}
	
	public void save(Screen screen) {
		screen.setSafeName(this.createSafeName(screen.getName()));
		screenRepository.save(screen);
	}
	
	public Screen findDefaultScreenForDashboard(Dashboard dashboard) {
		return screenRepository.findFirstByDashboardOrderByLocationAsc(dashboard);
	}
	
	public List<Screen> findScreensForDashboard(Dashboard dashboard){
		return screenRepository.findByDashboardOrderByLocationAsc(dashboard);
	}
	
	public Screen findBySafeName(String safeName) {
		return screenRepository.findBySafeName(safeName);
	}
	
}

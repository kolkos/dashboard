package nl.kolkos.dashboard.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		name = this.generateUniqueSafeName(name);
		
		return name;
	}
	
	/**
	 * This (recursive) method will check if the given panel id is unique, if not it will continue until a unique panel id is created
	 * @param panelId
	 * @return
	 */
	public String generateUniqueSafeName(String safeName) {
		// check if the name is already used
		if(dashboardRepository.findBySafeName(safeName) == null) {
			return safeName;
		} else {
			
			// check if the id ends with a number 
			Pattern pattern = Pattern.compile("(^.*)(_\\d{1,}$)");
			Matcher match = pattern.matcher(safeName);
			
			int nr = 0;
			// check if there is a match
			// if there is no match, it means there is no number suffix
			if(!match.find()) {
				// just add add the number one and we are done
				nr = 1;
			}else {
				String numberSuffix = match.group(2);
				numberSuffix = numberSuffix.replaceAll("_", "");
				nr = Integer.parseInt(numberSuffix);
				nr++;
				
				safeName = match.group(1);
			}
			
			return generateUniqueSafeName(safeName + "_" + nr);
			
		}
	}
	
	public void save(Dashboard dashboard) {
		dashboardRepository.save(dashboard);
	}
	
	public Dashboard findBySafeName(String safeName) {
		return dashboardRepository.findBySafeName(safeName);
	}
	
	public Iterable<Dashboard> findAll(){
		return dashboardRepository.findAll();
	}
	
	public Dashboard findById(long id) {
		return dashboardRepository.findById(id);
	}
	
	private void resetDefaultDashboards() {
		// loop through all registered dashboards and set default dashboard to false
		Iterable<Dashboard> dashboards = dashboardRepository.findAll();
		for(Dashboard dashboard : dashboards) {
			dashboard.setDefaultDashboard(false);
			dashboardRepository.save(dashboard);
		}
	}
	
	public void createNewDashboard(Dashboard dashboard) {
		// check if this is the (new) default dashboard
		if(dashboard.isDefaultDashboard()) {
			this.resetDefaultDashboards();
		}
		
		// check if the background image is filled
		if(dashboard.getBackgroundImage().length() < 1) {
			dashboard.setBackgroundImage(null);
		}
		
		dashboardRepository.save(dashboard);
	}
}

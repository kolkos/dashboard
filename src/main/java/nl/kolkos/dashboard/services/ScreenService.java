package nl.kolkos.dashboard.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		name = this.generateUniqueSafeName(name);
		
		return name;
	}
	
	public String generateUniqueSafeName(String safeName) {
		// check if the name is already used
		if(screenRepository.findBySafeName(safeName) == null) {
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
	
	public Screen getScreen(String safeName, Dashboard dashboard) {
		return screenRepository.findBySafeNameAndDashboard(safeName, dashboard);
	}
	
	public List<Screen> findAll(){
		return screenRepository.findAllByOrderByNameAsc();
	}
	
	public void saveNewScreen(Screen screen) {
		// get the last position for this dashboard
		int lastLocation = screenRepository.findFirstByDashboardOrderByLocationDesc(screen.getDashboard()).getLocation();
		lastLocation = lastLocation + 1;
		screen.setLocation(lastLocation);
		
		if(screen.getBackgroundImage().length() < 1) {
			screen.setBackgroundImage(null);
		}
		
		if(screen.getIcon().length() < 1) {
			screen.setIcon(null);
		}
		
		screenRepository.save(screen);
	}
}

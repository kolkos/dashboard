package nl.kolkos.dashboard.services;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
		if(screen.getSafeName() == null) {
			screen.setSafeName(this.createSafeName(screen.getName()));
		}
		
		if(screen.getIcon().length() < 1) {
			screen.setIcon(null);
		}
		
		if(screen.getBackgroundImage() != null && screen.getBackgroundImage().length() < 1) {
			screen.setBackgroundImage(null);
		}
		
		
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
	
	public Screen findBySafeName(String safeName) {
		return screenRepository.findBySafeName(safeName);
	}
	
	public List<Screen> findAll(){
		return screenRepository.findAllByOrderByNameAsc();
	}
	
	public List<Screen> findScreens(){
		List<Screen> screens = screenRepository.findAllByOrderByDashboardAscLocationAsc();
		
		// loop through screens
		for(Screen screen : screens) {
			// get the last screen for the dashboard
			if(screenRepository.findFirstByDashboardOrderByLocationDesc(screen.getDashboard()).getId() == screen.getId()) {
				screen.setLastScreen(true);
			}
		}
		
		return screens;
	}
	
	public void saveNewScreen(Screen screen) {
		// get the last position for this dashboard
		int lastLocation;
		try {
			lastLocation = screenRepository.findFirstByDashboardOrderByLocationDesc(screen.getDashboard()).getLocation();
			lastLocation = lastLocation + 1;
		} catch(NullPointerException e) {
			lastLocation = 0;
		}
		
		
		screen.setLocation(lastLocation);
		
		if(screen.getBackgroundImage() != null && screen.getBackgroundImage().length() < 1) {
			screen.setBackgroundImage(null);
		}
		
		if(screen.getIcon() != null && screen.getIcon().length() < 1) {
			screen.setIcon(null);
		}
		
		screenRepository.save(screen);
	}
	
	public void movePositionUp(Screen screenToMove, List<Screen> screens) {
		int newPosition = screenToMove.getLocation();
		
		// loop through the screens
		for(Screen screen : screens) {
			// check if the screen is the same as the screen we wish to move
			if(screen.getId() == screenToMove.getId()) {
				// set this screen to the new position
				// skip the other tasks
				screen.setLocation(newPosition);
				continue;
			}
			
			int thisScreenPosition = screen.getLocation();
			
			// now check the position of the screen and compare it to the screen to move
			if(thisScreenPosition == newPosition) {
				// location is equal to the new location, move one up
				screen.setLocation(screen.getLocation() + 1);
			} else if (thisScreenPosition > newPosition) {
				// position is greater than the new position
				// we will fix any gaps later
				screen.setLocation(screen.getLocation() + 1);
			} else {
				// location is smaller than the new position, don't move
				continue;
			}
		}
		
	}
	
	
	public void movePositionDown(Screen screenToMove, List<Screen> screens) {
		int newPosition = screenToMove.getLocation();
		
		// loop through the screens
		for(Screen screen : screens) {
			
			
			// check if the screen is the same as the screen we wish to move
			if(screen.getId() == screenToMove.getId()) {
				// set this screen to the new position
				screen.setLocation(newPosition);
				continue;
			}
			
			int thisScreenPosition = screen.getLocation();
			// change the order
			if(thisScreenPosition == newPosition) {
				// move one position down
				screen.setLocation(screen.getLocation() - 1);
			} else if (thisScreenPosition > newPosition) {
				screen.setLocation(screen.getLocation() + 1);
			}else {
				screen.setLocation(screen.getLocation() - 1);
			}
		}
		
	}
	
	public void fixPositions(List<Screen> screens) {
		// first sort on Location
		screens = screens.stream().sorted(Comparator.comparing(Screen::getLocation)).collect(Collectors.toList());
		// now reset the positions
		int position = 0;
		for(Screen screen : screens) {
			screen.setLocation(position);
			position++;
		}
	}
	
	public void saveNewLocations(List<Screen> screens) {
		this.fixPositions(screens);
		for(Screen screen : screens) {
			screenRepository.save(screen);
		}
	}
	
	public Screen findById(long id) {
		return screenRepository.findById(id);
	}
}

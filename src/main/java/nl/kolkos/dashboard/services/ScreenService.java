package nl.kolkos.dashboard.services;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.Screen;
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
		if(screen.getIcon().length() < 1) {
			screen.setIcon(null);
		}
		
		if(screen.getBackgroundImage() != null && screen.getBackgroundImage().length() < 1) {
			screen.setBackgroundImage(null);
		}
		
		
		screenRepository.save(screen);
	}
	
	public Screen findDefaultScreenForDashboard(Dashboard dashboard) {
		return screenRepository.findFirstByDashboardOrderByPositionAsc(dashboard);
	}
	
	public List<Screen> findScreensForDashboard(Dashboard dashboard){
		return screenRepository.findByDashboardOrderByPositionAsc(dashboard);
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
		List<Screen> screens = screenRepository.findAllByOrderByDashboardAscPositionAsc();
		
		// loop through screens
		for(Screen screen : screens) {
			// get the last screen for the dashboard
			if(screenRepository.findFirstByDashboardOrderByPositionDesc(screen.getDashboard()).getId() == screen.getId()) {
				screen.setLastScreen(true);
			}
		}
		
		return screens;
	}
	
	public void saveNewScreen(Screen screen) {
		// get the last position for this dashboard
		int lastPosition;
		try {
			lastPosition = screenRepository.findFirstByDashboardOrderByPositionDesc(screen.getDashboard()).getPosition();
			lastPosition = lastPosition + 1;
		} catch(NullPointerException e) {
			lastPosition = 0;
		}
		
		
		screen.setPosition(lastPosition);
		
		if(screen.getBackgroundImage() != null && screen.getBackgroundImage().length() < 1) {
			screen.setBackgroundImage(null);
		}
		
		if(screen.getIcon() != null && screen.getIcon().length() < 1) {
			screen.setIcon(null);
		}
		
		screenRepository.save(screen);
	}
	
	public void movePositionUp(Screen screenToMove, List<Screen> screens) {
		int newPosition = screenToMove.getPosition();
		
		// loop through the screens
		for(Screen screen : screens) {
			// check if the screen is the same as the screen we wish to move
			if(screen.getId() == screenToMove.getId()) {
				// set this screen to the new position
				// skip the other tasks
				screen.setPosition(newPosition);
				continue;
			}
			
			int thisScreenPosition = screen.getPosition();
			
			// now check the position of the screen and compare it to the screen to move
			if(thisScreenPosition == newPosition) {
				// Position is equal to the new Position, move one up
				screen.setPosition(screen.getPosition() + 1);
			} else if (thisScreenPosition > newPosition) {
				// position is greater than the new position
				// we will fix any gaps later
				screen.setPosition(screen.getPosition() + 1);
			} else {
				// Position is smaller than the new position, don't move
				continue;
			}
		}
		
	}
	
	
	public void movePositionDown(Screen screenToMove, List<Screen> screens) {
		int newPosition = screenToMove.getPosition();
		
		// loop through the screens
		for(Screen screen : screens) {
			
			
			// check if the screen is the same as the screen we wish to move
			if(screen.getId() == screenToMove.getId()) {
				// set this screen to the new position
				screen.setPosition(newPosition);
				continue;
			}
			
			int thisScreenPosition = screen.getPosition();
			// change the order
			if(thisScreenPosition == newPosition) {
				// move one position down
				screen.setPosition(screen.getPosition() - 1);
			} else if (thisScreenPosition > newPosition) {
				screen.setPosition(screen.getPosition() + 1);
			}else {
				screen.setPosition(screen.getPosition() - 1);
			}
		}
		
	}
	
	public void fixPositions(List<Screen> screens) {
		// first sort on Position
		screens = screens.stream().sorted(Comparator.comparing(Screen::getPosition)).collect(Collectors.toList());
		// now reset the positions
		int position = 0;
		for(Screen screen : screens) {
			screen.setPosition(position);
			position++;
		}
	}
	
	public void saveNewPositions(List<Screen> screens) {
		this.fixPositions(screens);
		for(Screen screen : screens) {
			screenRepository.save(screen);
		}
	}
	
	public Screen findById(long id) {
		return screenRepository.findById(id);
	}
}

package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.repositories.PanelRepository;

@Service
public class PanelService {
	@Autowired
	private PanelRepository panelRepository;
	
	@Autowired
	private ContentBuilderService contentBuilderService;
	
	
	
	private static final int MAX_ROWS = 8;
	private static final int MAX_COLUMNS = 8;
	
	public void save(Panel panel) {
		panelRepository.save(panel);
	}
	
	public Panel findById(long id) {
		return panelRepository.findById(id);
	}
	
	public void createNewPanel(Panel panel) {
		// set the default position attributes
		panel.setRowStart(1);
		panel.setColumnStart(1);
		panel.setHeight(1);
		panel.setWidth(1);
		
		// now get free position
		panel = this.findFreePositionForNewPanel(panel);
		
		// finally save it
		panelRepository.save(panel);
	}
	
	/**
	 * This method strips the spaces and replaces them with a underscore
	 * Then it will remove all characters but letters and numbers
	 * Finally it will call a method to ensure a unique id is generated
	 * @param name
	 * @return
	 */
	public String createSafeName(String name) {
		String safeName = name;
		
		safeName = safeName.replaceAll("\\s+", "_");
		safeName = safeName.replaceAll("[^A-Za-z0-9_]", "");
		
		safeName = this.generateUniqueSafeName(safeName);
		
		return safeName;
	}
	
	/**
	 * This (recursive) method will check if the given panel id is unique, if not it will continue until a unique panel id is created
	 * @param panelId
	 * @return
	 */
	private String generateUniqueSafeName(String panelId) {
		// check if the name is already used
		if(panelRepository.findBySafeName(panelId) == null) {
			return panelId;
		} else {
			
			// check if the id ends with a number 
			Pattern pattern = Pattern.compile("(^.*)(_\\d{1,}$)");
			Matcher match = pattern.matcher(panelId);
			
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
				
				panelId = match.group(1);
			}
			
			return generateUniqueSafeName(panelId + "_" + nr);
		}
	}
	
	public Panel findBySafeName(String panelId) {
		return panelRepository.findBySafeName(panelId);
	}
	
	
	public List<Panel> getPanelsForScreen(Screen screen){
		return panelRepository.findByScreen(screen);
	}
	
	public Panel findPanelByPanelIdAndScreen(String panelId, Screen screen) {
		return panelRepository.findBySafeNameAndScreen(panelId, screen);
	}
	
	public boolean checkNewPanelPositionIsAllowed(Panel panel) {
		boolean allowed = true;
		
		// calculate the new end position (row)
		int endPositionRow = (panel.getRowStart() - 1) + panel.getHeight();
		
		// calculate the new end position (column)
		int endPositionColumn = (panel.getColumnStart() - 1) + panel.getWidth();
		
		// check if the end position is outside the limits
		if(endPositionRow > MAX_ROWS || endPositionColumn > MAX_COLUMNS) {
			allowed = false;
		}
		
		return allowed;
	}
	
	public String updatePanelPosition(Panel panel) {
		String message = "";
		if(this.checkNewPanelPositionIsAllowed(panel)) {
			// create the alert 
			message = contentBuilderService.buildDismissibleAlert("Panel successfully updated.", "alert-success");
			
			// save the panel
			panelRepository.save(panel);
		} else {
			message = contentBuilderService.buildDismissibleAlert("This position is not allowed.", "alert-danger");
		}
		return message;
	}
	
	
	public Panel findFreePositionForNewPanel(Panel newPanel) {
		// by default the panel has row and column 1
		
		// get all the Panels for this screen
		List<Panel> currentPanels = panelRepository.findByScreen(newPanel.getScreen());
		List<String> takenPositions = this.getTakenPositions(currentPanels);
		
		newPanel = this.findFreePosition(newPanel, takenPositions);
		return newPanel;
	}
	
	/**
	 * This method will get the taken positions and will return it as a list
	 * It will loop through the panels currently added
	 * The position is a string determined by the row and the column
	 * For example row 2 with column 1 is position 21
	 */
	private List<String> getTakenPositions(List<Panel> currentPanels){
		List<String> takenPositions = new ArrayList<>();
		
		for(Panel panel : currentPanels) {
			// loop through the rows
			for(int row = panel.getRowStart(); row <= (panel.getRowStart() + panel.getHeight() - 1); row++) {
				// loop through columns
				for(int column = panel.getColumnStart(); column <= (panel.getColumnStart() + panel.getWidth() - 1); column++) {
					String position = "" + row + column;
					takenPositions.add(position);
				}
			}
		}
		
		return takenPositions;
	}
	
	/**
	 * This method loop through all the available positions on the screen
	 * It will stop when a free position is found
	 * If no free position is found, it will return 1, 1
	 * @param panel the new panel
	 * @param takenPositions the current taken positions (result from method getTakenPositions) 
	 * @return updated panel
	 */
	private Panel findFreePosition(Panel panel, List<String> takenPositions) {
		// loop through the rows
		for(int row = 1; row <= MAX_ROWS; row++) {
			for(int column = 1; column <= MAX_COLUMNS; column++) {
				String cursor = "" + row + column;
				// check if the cursor is on a free position
				if(! takenPositions.contains(cursor)) {
					// this position is free
					panel.setRowStart(row);
					panel.setColumnStart(column);
					
					return panel;
				}
			}
		}
		
		// if no free position could be found, we will return the default position (so 1,1)
		return panel;
	}
	
	public List<Panel> getPanels(String dashboardIdString, String screenIdString){
		// get all the panels
		List<Panel> panels = panelRepository.findAllByOrderByNameAsc();
		
		// now check if the dashboard filter string is not null
		if(dashboardIdString != null) {
			long dashboardId = Long.parseLong(dashboardIdString);
			panels = this.filterByDashboard(panels, dashboardId);
		}
		
		// same check for the screen id
		if(screenIdString != null) {
			long screenId = Long.parseLong(screenIdString);
			panels = this.filterByScreen(panels, screenId);
		}
		
		
		return panels;
	}
	
	public List<Panel> filterByDashboard(List<Panel> panels, long dashboardId){
		return panels.stream()
				.filter(x -> x.getScreen().getDashboard().getId() == dashboardId)
				.map(s -> s)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public List<Panel> filterByScreen(List<Panel> panels, long screenId){
		return panels.stream()
				.filter(x -> x.getScreen().getId() == screenId)
				.map(s -> s)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
}

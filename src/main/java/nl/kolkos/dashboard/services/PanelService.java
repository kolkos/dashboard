package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.repositories.PanelRepository;

@Service
public class PanelService {
	@Autowired
	private PanelRepository panelRepository;
	
	@Autowired
	private ContentBuilderService contentBuilderService;
	
	
	
	private static final int MAX_ROWS = 8;
	private static final int MAX_COLUMNS = 8;
	
	public String save(Panel panel) {
		// first create a Panel ID
		String panelId = this.createPanelId(panel.getName());
		
		// now check if this panel already exists
		Panel tempPanel = panelRepository.findByPanelIdAndScreen(panelId, panel.getScreen());
		String message = ""; 
		if(tempPanel != null) {
			message = contentBuilderService.buildDismissibleAlert("There is already a panel on this page with the same name.", "alert-danger");
		} else {
			// panel does not exist, add the panel ID
			panel.setPanelId(panelId);
			
			// save the panel
			panelRepository.save(panel);
			
			// build a message
			message = contentBuilderService.buildDismissibleAlert("Panel successfully created.", "alert-success");
		}
		tempPanel = null;
		
		
		return message;
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
	public String createPanelId(String name) {
		String panelId = name;
		
		panelId = panelId.replaceAll("\\s+", "_");
		panelId = panelId.replaceAll("[^A-Za-z0-9_]", "");
		
		panelId = this.generateUniquePanelId(panelId);
		
		return panelId;
	}
	
	/**
	 * This (recursive) method will check if the given panel id is unique, if not it will continue until a unique panel id is created
	 * @param panelId
	 * @return
	 */
	public String generateUniquePanelId(String panelId) {
		// check if the name is already used
		if(panelRepository.findByPanelId(panelId) == null) {
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
			
			return generateUniquePanelId(panelId + "_" + nr);
			
		}
	}
	
	
	
	
	public List<Panel> getPanelsForScreen(Screen screen){
		return panelRepository.findByScreen(screen);
	}
	
	public Panel findPanelByPanelIdAndScreen(String panelId, Screen screen) {
		return panelRepository.findByPanelIdAndScreen(panelId, screen);
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
	
}

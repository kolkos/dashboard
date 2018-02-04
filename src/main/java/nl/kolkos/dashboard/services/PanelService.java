package nl.kolkos.dashboard.services;

import java.util.List;

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
	
	public String createPanelId(String name) {
		name = name.replaceAll("\\s+", "_");
		name = name.replaceAll("[^A-Za-z0-9_]", "");
		
		return name;
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
	
}

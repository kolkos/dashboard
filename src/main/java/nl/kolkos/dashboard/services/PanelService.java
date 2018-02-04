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
	
	public void save(Panel panel) throws IllegalArgumentException {
		// first create a Panel ID
		String panelId = this.createPanelId(panel.getName());
		
		// now check if this panel already exists
		Panel tempPanel = panelRepository.findByPanelIdAndScreen(panelId, panel.getScreen());
		if(tempPanel != null) {
			throw new IllegalArgumentException("A panel with this name already exists on this page");
			
		}
		tempPanel = null;
		
		// panel does not exist, add the panel ID
		panel.setPanelId(panelId);
		
		// save the panel
		panelRepository.save(panel);
		
	}
	
	public String createPanelId(String name) {
		name = name.replaceAll("\\s+", "_");
		name = name.replaceAll("[^A-Za-z0-9_]", "");
		
		return name;
	}
	
	public List<Panel> getPanelsForScreen(Screen screen){
		return panelRepository.findByScreen(screen);
	}
	
	
}

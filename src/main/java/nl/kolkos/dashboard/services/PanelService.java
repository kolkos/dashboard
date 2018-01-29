package nl.kolkos.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Row;
import nl.kolkos.dashboard.repositories.PanelRepository;

@Service
public class PanelService {
	@Autowired
	private PanelRepository panelRepository;
	
	public void save(Panel panel) {
		panel.setSafeName(this.createSafeName(panel.getTitle()));
		panelRepository.save(panel);
	}
	
	public String createSafeName(String name) {
		name = name.replaceAll("\\s+", "_");
		name = name.replaceAll("[^A-Za-z0-9_]", "");
		
		return name;
	}
	
	public List<Panel> findPanelsForRow(Row row){
		return panelRepository.findByRowOrderByPosition(row);
	}
}

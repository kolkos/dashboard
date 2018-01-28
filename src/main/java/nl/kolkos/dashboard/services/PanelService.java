package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.repositories.PanelRepository;

@Service
public class PanelService {
	@Autowired
	private PanelRepository panelRepository;
	
	public void save(Panel panel) {
		panelRepository.save(panel);
	}
}

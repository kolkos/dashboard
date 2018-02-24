package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.DevicePanel;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.repositories.DevicePanelRepository;

@Service
public class DevicePanelService {
	@Autowired
	private DevicePanelRepository devicePanelRepository;
	
	public void save(DevicePanel contentDevice) {
		devicePanelRepository.save(contentDevice);
	}
	
	public DevicePanel findByPanel(Panel panel) {
		return devicePanelRepository.findByPanel(panel);
	}
}

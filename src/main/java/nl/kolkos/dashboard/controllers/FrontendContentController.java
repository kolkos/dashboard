package nl.kolkos.dashboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DevicePanel;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.services.DevicePanelService;
import nl.kolkos.dashboard.services.PanelService;

@Controller
@RequestMapping(path="/frontend/content")
public class FrontendContentController {
	
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private DevicePanelService devicePanelService;

	@RequestMapping(value = "/device/{panelId}", method = RequestMethod.GET)
	public @ResponseBody String loadDeviceContent(
			@PathVariable("panelId") String panelId) {
		
		// find the pnael
		Panel panel = panelService.findBySafeName(panelId);
		if(panel == null) {
			return "Panel not found";
		}
		
		// now get the ContentDevice by the panel
		DevicePanel devicePanel = devicePanelService.findByPanel(panel);
		if(devicePanel == null) {
			return "No device linked to this panel";
		}
		
		Device device = devicePanel.getDevice();
		
		String info = String.format("Template: %s", "TODO!");
		
		
		
		return info;
	}
	
}

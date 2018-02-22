package nl.kolkos.dashboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.objects.ContentDevice;
import nl.kolkos.dashboard.objects.Device;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.services.ContentDeviceService;
import nl.kolkos.dashboard.services.PanelService;

@Controller
@RequestMapping(path="/frontend/content")
public class FrontendContentController {
	
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private ContentDeviceService contentDeviceService;

	@RequestMapping(value = "/device/{panelId}", method = RequestMethod.GET)
	public @ResponseBody String loadDeviceContent(
			@PathVariable("panelId") String panelId) {
		
		// find the pnael
		Panel panel = panelService.findByPanelId(panelId);
		if(panel == null) {
			return "Panel not found";
		}
		
		// now get the ContentDevice by the panel
		ContentDevice contentDevice = contentDeviceService.findByPanel(panel);
		if(contentDevice == null) {
			return "No device linked to this panel";
		}
		
		Device device = contentDevice.getDevice();
		
		String info = String.format("Template: %s", device.getSubDeviceType().getTemplatePage());
		
		
		
		return info;
	}
	
}
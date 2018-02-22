package nl.kolkos.dashboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.kolkos.dashboard.objects.Panel;

@Controller
@RequestMapping(path="/frontend/content")
public class FrontendContentController {

	@RequestMapping(value = "/device/{panelId}", method = RequestMethod.POST)
	public String loadDeviceContent() {
		
		return "redirect:/config/panel/results";
	}
	
}

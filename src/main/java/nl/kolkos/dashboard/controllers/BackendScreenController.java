package nl.kolkos.dashboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.BackendService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.ScreenService;

@Controller
@RequestMapping(path="/config/screen")
public class BackendScreenController {
	
	@Autowired
	private BackendService backendService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	@RequestMapping(value = "/generate_safe_name", method = RequestMethod.GET)
	public @ResponseBody String generateUniquePanelId(
			@RequestParam("name") String name) {
		String safeName = screenService.createSafeName(name);
		return safeName;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createNewScreen(
			Model model) {
		
		Screen screen = new Screen();
		model.addAttribute("screen", screen);
		
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
				
		List<String> backgrounds = backendService.loadBackgroundImages();
		model.addAttribute("backgrounds", backgrounds);
		
		return "backend/screen_add_form";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createNewPanel(@ModelAttribute Screen screen) {
		screenService.saveNewScreen(screen);
		return "redirect:/config/screen/results";
	}
}

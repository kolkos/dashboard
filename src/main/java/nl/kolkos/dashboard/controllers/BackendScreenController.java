package nl.kolkos.dashboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String listScreens(
			Model model) {
		
		List<Screen> screens = screenService.findScreens();
		model.addAttribute("screens", screens);
		
		return "backend/screen_result";
	}
	
	@RequestMapping(value = "/position/{direction}/{screenId}", method = RequestMethod.GET)
	public String moveScreenUp(
			@PathVariable("screenId") long screenId,
			@PathVariable("direction") String direction,
			Model model) {
		
		// get the screen
		if(screenService.findById(screenId) == null) {
			model.addAttribute("message", "The dashboard could not be found");
			return "backend/error";
		}
		
				
		Screen screenToMove = screenService.findById(screenId);
		List<Screen> screens = screenService.findScreensForDashboard(screenToMove.getDashboard());
		
		// determine where to move the screen
		if(direction.toUpperCase().equals("UP")) {
			screenToMove.setLocation(screenToMove.getLocation() - 1);
						
			// now do the actual moving
			screenService.movePositionUp(screenToMove, screens);
			
		}else {
			screenToMove.setLocation(screenToMove.getLocation() + 1);
			
			// now do the actual moving
			screenService.movePositionDown(screenToMove, screens);
		}
		
		// save the new positions
		screenService.saveNewLocations(screens);
		
		
		
		
		return "redirect:/config/screen/results";
	}
	
}

package nl.kolkos.dashboard.controllers.backend;

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

import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.services.BackendService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.ScreenService;

@Controller
@RequestMapping(path="/config/screen")
public class ScreenController {
	
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
		
		model.addAttribute("title", "Create new screen");
		model.addAttribute("description", "With this page you can create a new screen. A screen contains a collection of panels.");
		
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
		
		model.addAttribute("title", "Screens");
		model.addAttribute("description", "This page shows the created screens. Click on the pencil to edit a screen.");
		
		List<Screen> screens = screenService.findScreens();
		model.addAttribute("screens", screens);
		
		return "backend/screen_result";
	}
	
	
	
	@RequestMapping(value = "/position/{direction}/{screenId}", method = RequestMethod.GET)
	public String moveScreen(
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
			screenToMove.setPosition(screenToMove.getPosition() - 1);
						
			// now do the actual moving
			screenService.movePositionUp(screenToMove, screens);
			
		}else {
			screenToMove.setPosition(screenToMove.getPosition() + 1);
			
			// now do the actual moving
			screenService.movePositionDown(screenToMove, screens);
		}
		
		// save the new positions
		screenService.saveNewPositions(screens);
		
		
		
		
		return "redirect:/config/screen/results";
	}
	
	@RequestMapping(value = "/edit/{screenId}", method = RequestMethod.GET)
	public String editScreenForm(
			@PathVariable("screenId") long screenId,
			Model model) {
		
		model.addAttribute("title", "Edit screen");
		model.addAttribute("description", "Edit the details of this screen.");
		
		// get the screen
		if(screenService.findById(screenId) == null) {
			model.addAttribute("message", "The screen could not be found");
			return "backend/error";
		}
		
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		List<String> backgrounds = backendService.loadBackgroundImages();
		model.addAttribute("backgrounds", backgrounds);
		
		Screen screen = screenService.findById(screenId);
		model.addAttribute("screen", screen);
		
		
		return "backend/screen_edit_form";
	}
	
	@RequestMapping(value = "/edit/{screenId}", method = RequestMethod.POST)
	public String editScreen(
			@PathVariable("screenId") long screenId,
			@ModelAttribute Screen screen,
			Model model) {
		
		if(screenService.findById(screenId) == null) {
			model.addAttribute("message", "The screen could not be found");
			return "backend/error";
		}
		
		// set the dashboard id to make sure the dashboard is being updated and not created
		screen.setId(screenId);
		
		screenService.save(screen);
		
		return "redirect:/config/screen/results";
	}
	
}

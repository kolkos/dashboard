package nl.kolkos.dashboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.ScreenService;



@Controller
@RequestMapping(path="/")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	
	@RequestMapping(value = "/{safeName}", method = RequestMethod.GET)
	public String redirectToFirstScreen(@PathVariable("safeName") String safeName, Model model) {
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeName);
		
		// now get the default page
		// this is the page with the lowest position number
		Screen screen = screenService.findDefaultScreenForDashboard(dashboard);
		
		// now createe the redirecting ur;
		String redirect = String.format("redirect:/%s/%s", safeName, screen.getSafeName());
		
		
		return redirect;
	}
	
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}", method = RequestMethod.GET)
	public String buildScreen(@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			Model model) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		
		// get the screen
		Screen screen = screenService.findBySafeName(safeNameScreen);
		model.addAttribute("screen", screen);
				
		// now get all the screens for this dashboard
		List<Screen> screens = screenService.findScreensForDashboard(dashboard);
		model.addAttribute("screens", screens);
		
		
		
		
		return "show_screen";
	}
	
}

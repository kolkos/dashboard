package nl.kolkos.dashboard.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DeviceService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;


@Controller
@RequestMapping(path="/dashboard")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private PanelService panelService;
	
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}/results", method = RequestMethod.GET)
	public String loadPanelsForScreen(@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			Model model) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		model.addAttribute("dashboard", dashboard);
		
		// get the screen
		Screen screen = screenService.getScreen(safeNameScreen, dashboard);
		
		// get the panels for this screen
		List<Panel> panels = panelService.getPanelsForScreen(screen);
		model.addAttribute("panels", panels);
		
		return "panels_list_frontend";
	}
	
	@RequestMapping(value = "/{safeName}", method = RequestMethod.GET)
	public String redirectToFirstScreen(@PathVariable("safeName") String safeName, Model model) {
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeName);
		model.addAttribute("dashboard", dashboard);
		
		// now get the default page
		// this is the page with the lowest position number
		Screen screen = screenService.findDefaultScreenForDashboard(dashboard);
		model.addAttribute("screen", screen);
		
		// now createe the redirecting ur;
		String redirect = String.format("redirect:/dashboard/%s/%s", safeName, screen.getSafeName());
		
		
		return redirect;
	}
	
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}", method = RequestMethod.GET)
	public String buildScreen(
			@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			Model model) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		if(dashboard == null) {
			System.out.println("Dashboard!?");
		}
		model.addAttribute("dashboard", dashboard);
		
		// get the screen
		Screen screen = screenService.findBySafeName(safeNameScreen);
		
		
		model.addAttribute("screen", screen);
		
		// get the screens
		List<Screen> screens = screenService.findScreensForDashboard(dashboard);
		model.addAttribute("screens", screens);
		
		deviceService.getCurrentDeviceInfo();
		
		return "show_screen";
	}
	
}

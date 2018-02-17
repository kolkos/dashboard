package nl.kolkos.dashboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;

@Controller
@RequestMapping(path="/config/layout")
public class BackendLayoutController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private PanelService panelService;
	
	/**
	 * This loads the overal configuration page for the selected dashboard / screen
	 * @param safeNameDashboard
	 * @param safeNameScreen
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}", method = RequestMethod.GET)
	public String loadConfigPageForScreen(@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			Model model) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		model.addAttribute("dashboard", dashboard);
		
		// get the screen
		Screen screen = screenService.getScreen(safeNameScreen, dashboard);
		model.addAttribute("screen", screen);
		
		Panel panel = new Panel();
		model.addAttribute("panel", panel);
		
		
		return "backend/panel_edit";
	}
	
	/**
	 * This creates a result page for the panels
	 * @param safeNameDashboard
	 * @param safeNameScreen
	 * @param model
	 * @return
	 */
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
		
		return "backend/panels_list_backend";
	}
	
	/**
	 * This creates a new panel
	 * @param safeNameDashboard
	 * @param safeNameScreen
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}/new", method = RequestMethod.POST)
	public @ResponseBody String createNewPanel
			(@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			@RequestParam("name") String name,
			@RequestParam("row") int row,
			@RequestParam("column") int column,
			@RequestParam("height") int height,
			@RequestParam("width") int width) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		
		// get the screen
		Screen screen = screenService.getScreen(safeNameScreen, dashboard);
		
		// create a new panel object
		Panel panel = new Panel();
		panel.setScreen(screen);
		panel.setName(name);
		panel.setRowStart(row);
		panel.setColumnStart(column);
		panel.setHeight(height);
		panel.setWidth(width);
				
		return panelService.save(panel);
	}
	
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}/update", method = RequestMethod.POST)
	public @ResponseBody String updateThePanel
			(@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			@RequestParam("id") String panelId,
			@RequestParam("row") int row,
			@RequestParam("column") int column,
			@RequestParam("height") int height,
			@RequestParam("width") int width) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		
		// get the screen
		Screen screen = screenService.getScreen(safeNameScreen, dashboard);
		
		// finally get the panel
		Panel panel = panelService.findPanelByPanelIdAndScreen(panelId, screen);
		panel.setRowStart(row);
		panel.setColumnStart(column);
		panel.setHeight(height);
		panel.setWidth(width);
		
		
		return panelService.updatePanelPosition(panel);
	}
}

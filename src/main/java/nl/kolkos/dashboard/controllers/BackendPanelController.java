package nl.kolkos.dashboard.controllers;

import java.util.ArrayList;
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
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;

@Controller
@RequestMapping(path="/config/panel")
public class BackendPanelController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private ContentTypeService contentTypeService;
	
	@Autowired
	private PanelService panelService;
	
	
	
	/*
	 * =================================================
	 * Add panels
	 * =================================================
	 */
	
	/**
	 * Build the form screen to create a panel
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createNewPanelForm(
			@RequestParam(value = "dashboardId", required = false) String dashboardId,
			@RequestParam(value = "screenId", required = false) String screenId,
			Model model) {
		
		Panel panel = new Panel();
		model.addAttribute("panel", panel);
		
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		List<Screen> screens = new ArrayList<>();
		if(dashboardId!=null) {
			// find the Dashboard by id
			long dashId = Long.parseLong(dashboardId);
			Dashboard dashboard = dashboardService.findById(dashId);
			
			screens = screenService.findScreensForDashboard(dashboard);
			model.addAttribute("dashId", dashId);
		}
		
		if(screenId != null) {
			model.addAttribute("screenId", Long.parseLong(screenId));
		}
		
		model.addAttribute("contentTypes", contentTypeService.findAll());
		
		
		model.addAttribute("screens", screens);
		
		return "backend/panel_add_form";
	}
	
	/**
	 * Handle the post for creating a new panel
	 * @param panel
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createNewPanel(@ModelAttribute Panel panel) {
		panelService.createNewPanel(panel);
		return "redirect:/config/panel/add";
	}
	
	/**
	 * This method gets the generated panel id. jQuery is used to load it in the panelId field
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/generate_panel_id", method = RequestMethod.GET)
	public @ResponseBody String generateUniquePanelId(
			@RequestParam("name") String name) {
		String panelId = panelService.createPanelId(name);
		return panelId;
	}
	
	/*
	 * =================================================
	 * List panels
	 * =================================================
	 */
	
	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String loadPanelResults(
			@RequestParam(value = "dashboardId", required = false) String dashboardId,
			@RequestParam(value = "screenId", required = false) String screenId,
			Model model) {
		
		
		// check if the dashboard id is empty
		if(dashboardId != null && dashboardId.equals("")) {
			// id is empty, fill it with null again
			dashboardId = null;
		}
		
		// same trick for the screen id
		if(screenId != null && screenId.equals("")) {
			screenId = null;
		}
		
		
		// get the dashboards
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		List<Screen> screens = screenService.findAll();
		// if a dashboard id has been set, filter the screens
		if(dashboardId != null) {
			// find the Dashboard by id
			long dashId = Long.parseLong(dashboardId);
			Dashboard dashboard = dashboardService.findById(dashId);
			
			screens = screenService.findScreensForDashboard(dashboard);
			model.addAttribute("dashId", dashId);
		}
		model.addAttribute("screens", screens);
		
		if(screenId != null) {
			model.addAttribute("screenId", Long.parseLong(screenId));
		}
		
		// get the panels
		List<Panel> panels = panelService.getPanels(dashboardId, screenId);
		model.addAttribute("panels", panels);
		
		return "backend/panel_result";
	}
	
}

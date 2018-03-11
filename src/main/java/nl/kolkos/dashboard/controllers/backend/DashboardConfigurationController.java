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
import nl.kolkos.dashboard.services.BackendService;
import nl.kolkos.dashboard.services.DashboardService;

@Controller
@RequestMapping(path="/config/dashboard")
public class DashboardConfigurationController {
	
	@Autowired
	private DashboardService dashboardService;
	
	
	@Autowired
	private BackendService backendService;
	
	
	
	@RequestMapping(value = "/generate_safe_name", method = RequestMethod.GET)
	public @ResponseBody String generateUniquePanelId(
			@RequestParam("name") String name) {
		String safeName = dashboardService.createSafeName(name);
		return safeName;
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createNewDashboardForm(
			Model model) {
		
		model.addAttribute("title", "Create new dashboard");
		model.addAttribute("description", "This page let's you create a new dashboard. A dashboard is a set of screens.");
		
		Dashboard dashboard = new Dashboard();
		model.addAttribute("dashboard", dashboard);
		
				
		List<String> backgrounds = backendService.loadBackgroundImages();
		model.addAttribute("backgrounds", backgrounds);
		
		return "backend/dashboard_add_form";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createNewDashboard(@ModelAttribute Dashboard dashboard) {
		dashboardService.saveDashboard(dashboard);
		return "redirect:/config/dashboard/results";
	}
	
	
	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String listDashboards(
			Model model) {
		
		model.addAttribute("title", "Dashboards");
		model.addAttribute("description", "This page shows the created dashboards. Click on the pencil to edit a dashboard.");
		
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		return "backend/dashboard_result";
	}
	
	@RequestMapping(value = "/edit/{dashboardId}", method = RequestMethod.GET)
	public String editDashboardForm(
			@PathVariable("dashboardId") long dashboardId,
			Model model) {
		
		model.addAttribute("title", "Edit dashboard");
		model.addAttribute("description", "On this page you can edit the settings of the chosen dashboard.");
		
		if(dashboardService.findById(dashboardId) == null) {
			model.addAttribute("message", "The dashboard could not be found");
			return "backend/error";
		}
		
		Dashboard dashboard = dashboardService.findById(dashboardId);
		model.addAttribute("dashboard", dashboard);
		
		List<String> backgrounds = backendService.loadBackgroundImages();
		model.addAttribute("backgrounds", backgrounds);
		
		
		return "backend/dashboard_edit_form";
	}
	
	@RequestMapping(value = "/edit/{dashboardId}", method = RequestMethod.POST)
	public String editDashboard(
			@PathVariable("dashboardId") long dashboardId,
			@ModelAttribute Dashboard dashboard,
			Model model) {
		
		if(dashboardService.findById(dashboardId) == null) {
			model.addAttribute("message", "The dashboard could not be found");
			return "backend/error";
		}
		
		// set the dashboard id to make sure the dashboard is being updated and not created
		dashboard.setId(dashboardId);
		
		//Dashboard dashboard = dashboardService.findById(dashboardId);
		dashboardService.saveDashboard(dashboard);
		
		
		return "redirect:/config/dashboard/results";
	}
}

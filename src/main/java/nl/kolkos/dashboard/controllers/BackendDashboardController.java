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
import nl.kolkos.dashboard.services.BackendService;
import nl.kolkos.dashboard.services.DashboardService;

@Controller
@RequestMapping(path="/config/dashboard")
public class BackendDashboardController {
	
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
		
		Dashboard dashboard = new Dashboard();
		model.addAttribute("dashboard", dashboard);
		
				
		List<String> backgrounds = backendService.loadBackgroundImages();
		model.addAttribute("backgrounds", backgrounds);
		
		return "backend/dashboard_add_form";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createNewDashboard(@ModelAttribute Dashboard dashboard) {
		dashboardService.createNewDashboard(dashboard);
		return "redirect:/config/dashboard/results";
	}
}

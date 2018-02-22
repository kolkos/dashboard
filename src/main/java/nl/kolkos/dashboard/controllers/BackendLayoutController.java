package nl.kolkos.dashboard.controllers;

import java.util.ArrayList;
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

	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editLayoutPage(
			@RequestParam(value = "screenId", required = false) String screenId,
			@RequestParam(value = "dashboardId", required = false) String dashboardId,
			Model model) {
		
		model.addAttribute("title", "Edit screen layout");
		model.addAttribute("description", "Change the layout of a screen. After selecting a screen, the screen layout will be loaded.");
		
		// check if the dashboard id is empty
		if(dashboardId != null && !dashboardId.equals("")) {
			long dashboardIdLong = Long.parseLong(dashboardId);
			model.addAttribute("dashboardId", dashboardIdLong);
		}
		
		// check if the screen id is empty
		if(screenId != null && !screenId.equals("")) {
			long screenIdLong = Long.parseLong(screenId);
			model.addAttribute("screenId", screenIdLong);
		}
		
		// get the dashboards
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		// get the screens
		List<Screen> screens = screenService.findScreens();
		model.addAttribute("screens", screens);
		
		
		
		
		return "backend/layout_edit";
	}
	
	
	@RequestMapping(value = "/loadPanelForm", method = RequestMethod.GET)
	public String loadPanelForm(
			@RequestParam(value = "panelId", required = false) String panelId,
			Model model) {
		
		if(panelId.equals("NONE")) {
			model.addAttribute("message", "Click on a panel to load the form.");
			model.addAttribute("alertClass", "alert alert-info");
			return "backend/layout_edit_panel_form";
		}
		
		
		Panel panel = panelService.findByPanelId(panelId);
		if(panel == null) {
			model.addAttribute("message", "Panel could not be found.");
			model.addAttribute("alertClass", "alert alert-danger");
			return "backend/layout_edit_panel_form";
		}
		model.addAttribute("panel", panel);
		
		
		
		return "backend/layout_edit_panel_form";
	}
	
	@RequestMapping(value = "/loadPanelsForScreen", method = RequestMethod.GET)
	public String loadPanelsForScreen(
			@RequestParam(value = "screenId", required = false) long screenId,
			Model model) {
		
		Screen screen = screenService.findById(screenId);
		if(screen == null) {
			model.addAttribute("message", "This screen could not be found");
			return "backend/layout_preview_screen";
		}
		model.addAttribute("screen", screen);
		
		// load panels for screen
		List<Panel> panels = panelService.getPanelsForScreen(screen);
		model.addAttribute("panels", panels);
		
		return "backend/layout_preview_screen";
	}
	
	@RequestMapping(value = "/movePanel", method = RequestMethod.GET)
	public String movePanel(
			@RequestParam(value = "panelId", required = true) String panelId,
			@RequestParam(value = "rowStart", required = true) int rowStart,
			@RequestParam(value = "columnStart", required = true) int columnStart,
			@RequestParam(value = "height", required = true) int height,
			@RequestParam(value = "width", required = true) int width,
			Model model) {
		
		Panel panel = panelService.findByPanelId(panelId);
		if(panel == null) {
			model.addAttribute("message", "Panel could not be found.");
			model.addAttribute("alertClass", "alert alert-danger");
			return "backend/layout_edit_panel_form";
		}
		
		// set new values
		panel.setRowStart(rowStart);
		panel.setColumnStart(columnStart);
		panel.setHeight(height);
		panel.setWidth(width);
		
		// save the panel
		panelService.save(panel);
		
		// set the message
		model.addAttribute("message", "Panel updated.");
		model.addAttribute("alertClass", "alert alert-info");
		
		// finally set the panel
		model.addAttribute("panel", panel);
		return "backend/layout_edit_panel_form";
	}
	
	
	
}

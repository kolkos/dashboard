package nl.kolkos.dashboard.controllers;

import java.util.ArrayList;
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
import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DevicePanel;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.objects.Button;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DevicePanelService;
import nl.kolkos.dashboard.services.DeviceService;
import nl.kolkos.dashboard.services.DomoticzSyncService;
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
	
	@Autowired
	private DomoticzSyncService domoticzSyncService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private DevicePanelService devicePanelService;
	
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
			@RequestParam(value = "dashboardId", required = false) Long dashboardId,
			@RequestParam(value = "screenId", required = false) Long screenId,
			Model model) {
		
		model.addAttribute("title", "Create new panel");
		model.addAttribute("description", "With this page you can create a new panel.");
		
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		// find the screens for the dashboard
		List<Screen> screens = new ArrayList<>();
		if(dashboardId != null) {
			// find the Dashboard by id
			
			Dashboard dashboard = dashboardService.findById(dashboardId);
			
			screens = screenService.findScreensForDashboard(dashboard);
			model.addAttribute("dashboardId", dashboardId);
		}
		model.addAttribute("screens", screens);
		
		// create new panel for the form
		Panel panel = new Panel();
		
		
		if(screenId != null) {
			// screen id is set, add the screen to the panel
			// first find the Screen
			Screen screen = screenService.findById(screenId);
			// check if the screen is found
			if(screen != null) {
				// there is a screen, add it to the panel
				panel.setScreen(screen);
			}
			
			model.addAttribute("screenId", screenId);
		}
		model.addAttribute("panel", panel);
		
		model.addAttribute("contentTypes", contentTypeService.findAll());
		
		
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
		return "redirect:/config/panel/results";
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
		
		model.addAttribute("title", "Panels");
		model.addAttribute("description", "The panels created are listed below. You can filter the panels by using the form below.");
		
		// create a button
		Button newPanelButton = new Button("Create new panel", "btn-success", "/config/panel/add", "oi oi-plus");
		List<Button> buttons =  new ArrayList<>();
		buttons.add(newPanelButton);
		model.addAttribute("buttons", buttons);
		
		
		
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
	
	@RequestMapping(value = "/edit/{panelId}", method = RequestMethod.GET)
	public String editPanelForm(
			@PathVariable("panelId") long panelId,
			@RequestParam(value = "dashboardId", required = false) String dashboardId,
			Model model) {
		
		model.addAttribute("title", "Edit panel");
		model.addAttribute("description", "Edit the details of this panel.");
		
		// check if the dashboard id is empty
		if(dashboardId != null && dashboardId.equals("")) {
			// id is empty, fill it with null again
			dashboardId = null;
		}
		
		Panel panel = panelService.findById(panelId);
		if(panel == null) {
			model.addAttribute("message", "The panel could not be found");
			return "backend/error";
		}
		model.addAttribute("panel", panel);
		
		
		Iterable<Dashboard> dashboards = dashboardService.findAll();
		model.addAttribute("dashboards", dashboards);
		
		// get the screens
		List<Screen> screens = new ArrayList<>();
		if(dashboardId != null) {
			// find the Dashboard by id
			long dashId = Long.parseLong(dashboardId);
			Dashboard dashboard = dashboardService.findById(dashId);
			
			screens = screenService.findScreensForDashboard(dashboard);
			model.addAttribute("dashId", dashId);
			
			
		}else {
			screens = screenService.findScreens();
			
			model.addAttribute("dashId", panel.getScreen().getDashboard().getId());
		}
		model.addAttribute("screens", screens);
		model.addAttribute("contentTypes", contentTypeService.findAll());
		
		return "backend/panel_edit_form";
	}
	
	@RequestMapping(value = "/edit/{panelId}", method = RequestMethod.POST)
	public String editPanel(
			@PathVariable("panelId") long panelId,
			@ModelAttribute Panel panel,
			Model model) {
		
		
		if(panelService.findById(panelId) == null) {
			model.addAttribute("message", "The panel could not be found");
			return "backend/error";
		}
		
		
		// set the dashboard id to make sure the dashboard is being updated and not created
		panel.setId(panelId);
		
		panelService.save(panel);
		
		return "redirect:/config/panel/results";
	}
	
	@RequestMapping(value = "/content/Device/{panelId}", method = RequestMethod.GET)
	public String selectDeviceForm(
			@PathVariable("panelId") long panelId,
			Model model) {
		
		model.addAttribute("title", "Edit panel content");
		model.addAttribute("description", "This page let's you select a Domoticz Device to display.");
		
		// sync the devices
		domoticzSyncService.syncDevices();
		
		// now get the devices
		List<Device> devices = deviceService.findAllDevices();
		model.addAttribute("devices", devices);
		
		// get the panel
		Panel panel = panelService.findById(panelId);
		if(panel == null) {
			model.addAttribute("message", "The panel could not be found");
			return "backend/error";
		}
		model.addAttribute("panel", panel);
		
		
		// check if there is a ContentDevice for this panel
		DevicePanel devicePanel = devicePanelService.findByPanel(panel);
		if(devicePanel == null) {
			// does not exist, create a new one
			devicePanel = new DevicePanel();
		}
		
		// create the content device
		model.addAttribute("contentDevice", devicePanel);
		
		

		return "backend/panel_content_device_form";
	}
	
	@RequestMapping(value = "/content/Device/{panelId}", method = RequestMethod.POST)
	public String selectDevice(
			@ModelAttribute DevicePanel devicePanel,
			@PathVariable("panelId") long panelId,
			Model model) {
		
		model.addAttribute("title", "Edit panel content");
		model.addAttribute("description", "This page let's you select a Domoticz Device to display.");
				
		// get the panel
		Panel panel = panelService.findById(panelId);
		if(panel == null) {
			model.addAttribute("message", "The panel could not be found");
			return "backend/error";
		}
		
		// link the panel to the content device
		devicePanel.setPanel(panel);
		
		// save it
		devicePanelService.save(devicePanel);
		

		return "redirect:/config/panel/results";
	}
}

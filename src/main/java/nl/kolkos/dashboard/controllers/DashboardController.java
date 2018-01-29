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

import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Page;
import nl.kolkos.dashboard.objects.PageRow;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Row;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.RowService;
import nl.kolkos.dashboard.services.ScreenService;


@Controller
@RequestMapping(path="/dashboard")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private RowService rowService;
	
	@Autowired
	private PanelService panelService;
	
	
	
	@RequestMapping(value = "/{safeName}", method = RequestMethod.GET)
	public String redirectToFirstScreen(@PathVariable("safeName") String safeName, Model model) {
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeName);
		
		// now get the default page
		// this is the page with the lowest position number
		Screen screen = screenService.findDefaultScreenForDashboard(dashboard);
		
		// now createe the redirecting ur;
		String redirect = String.format("redirect:/dashboard/%s/%s", safeName, screen.getSafeName());
		
		
		return redirect;
	}
	
	@RequestMapping(value = "/{safeNameDashboard}/{safeNameScreen}", method = RequestMethod.GET)
	public String buildScreen(@PathVariable("safeNameDashboard") String safeNameDashboard,
			@PathVariable("safeNameScreen") String safeNameScreen,
			Model model) {
		
		// get the dashboard
		Dashboard dashboard = dashboardService.findBySafeName(safeNameDashboard);
		model.addAttribute("dashboard", dashboard);
		// get the screen
		Screen screen = screenService.findBySafeName(safeNameScreen);
				
		// now get all the screens for this dashboard
		List<Screen> screens = screenService.findScreensForDashboard(dashboard);
		
		// create a Page object
		Page page = new Page();
		page.setScreen(screen);
		page.setScreens(screens);
		
		
		// now get the rows for this screen
		List<Row> rows = rowService.findRowsForScreen(screen);
		List<PageRow> pageRows = new ArrayList<>();
		// loop through the rows
		for(Row row : rows) {
			// create a PageRow object
			PageRow pageRow = new PageRow();
			pageRow.setRow(row);
			
			// now get the panels for this object
			List<Panel> panels = panelService.findPanelsForRow(row);
			pageRow.setPanels(panels);
			
			// add to the list
			pageRows.add(pageRow);
		}
		
		page.setPageRows(pageRows);
		
		// add to the page
		model.addAttribute("page", page);
		
		
		return "show_screen";
	}
	
}

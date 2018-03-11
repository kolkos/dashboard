package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.objects.InstallLogLine;

/**
 * This service adds a set of default data
 * @author antonvanderkolk
 *
 */
@Service
public class InstallerService {
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private PanelService panelService;
	
	private List<InstallLogLine> logLines = new ArrayList<>();
	
	
	
	public List<InstallLogLine> installDefaultData() {
		logLines = new ArrayList<>();
		logLines.add(new InstallLogLine("General", "Starting installation", ""));
		
		this.createDashboards();
		this.createScreens();
		this.createExamplePanels();

		
		
		return logLines;
	}
	
	private void createDashboards() {
		logLines.add(new InstallLogLine("Dashboard", "Creating default dashboard(s)", "Creating the default dashboards."));
		logLines.add(new InstallLogLine("Dashboard", "Creating dashboard 'Default'", ""));
		
		
		// check if the dashboard exists
		if(dashboardService.findBySafeName("Default") == null) {
			logLines.add(new InstallLogLine("Dashboard", "'Default' dashboard does not exist.", ""));
			
			// create a Dashboard
			Dashboard defaultDashboard = new Dashboard();
			defaultDashboard.setName("Default");
			defaultDashboard.setSafeName(dashboardService.createSafeName(defaultDashboard.getName()));
			defaultDashboard.setDefaultDashboard(true);
			dashboardService.save(defaultDashboard);
			
			String dashboardLine = String.format("Dashboard name: '%s'.%n"
					+ "Dasboard safe name: '%s'.%n"
					+ "Default dashboard: '%s'.",
					defaultDashboard.getName(),
					defaultDashboard.getSafeName(),
					defaultDashboard.isDefaultDashboard());
			
			logLines.add(new InstallLogLine("Dashboard", "'Default' Dashboard created", dashboardLine));
		}else {
			logLines.add(new InstallLogLine("Dashboard", "'Default' dashboard does exists.", "Skip creating this dashboard."));
		}
		logLines.add(new InstallLogLine("Dashboard", "All dashboards created.", "Finished creating dashboards"));
				
	}
	
	private void createScreens() {
		logLines.add(new InstallLogLine("Screen", "Creating some screens", "Creating a couple of default screens."));
		
		logLines.add(new InstallLogLine("Screen", "Loading the dashboard 'Default'", ""));
		Dashboard defaultDashboard = dashboardService.findBySafeName("Default");
		if(defaultDashboard == null) {
			logLines.add(new InstallLogLine("Screen", "Error loading default dashboard", "Dashboard could not be found! Skip creating screens..."));
			return;
		}
		
		List<Screen> screens = new ArrayList<>();
		
		Screen homeScreen = new Screen();
		homeScreen.setName("Home");
		homeScreen.setSafeName("Home");
		homeScreen.setIcon("fas fa-home");
		homeScreen.setPosition(0);
		homeScreen.setDashboard(defaultDashboard);
		screens.add(homeScreen);
				
		Screen livingRoomScreen = new Screen();
		livingRoomScreen.setName("Living room");
		livingRoomScreen.setSafeName("Living_room");
		livingRoomScreen.setIcon("fas fa-glass-martini");
		livingRoomScreen.setPosition(1);
		livingRoomScreen.setDashboard(defaultDashboard);
		screens.add(livingRoomScreen);
		
		// now loop through the screens
		for(Screen screen : screens) {
			
			// check if the screen already exists
			if(screenService.findBySafeName(screen.getSafeName()) != null) {
				// screen exists
				logLines.add(new InstallLogLine("Screen", "Screen already exists", String.format("Skip creating screen '%s'.", screen.getName())));
				continue;
			}
			// Screen does not exist, create a line for the log
			String screenLine = String.format("Screen name: '%s'.%n"
					+ "Safe name: '%s'.%n"
					+ "Icon: '%s'.%n"
					+ "Location: %d.%n"
					+ "Dashboard: '%s'.", 
					screen.getName(),
					screen.getSafeName(),
					screen.getIcon(),
					screen.getPosition(),
					screen.getDashboard().getName());
			
			screenService.save(screen);
			
			logLines.add(new InstallLogLine("Screen", "Screen created", screenLine));
			
		}
		
		
		logLines.add(new InstallLogLine("Screen", "Finished creating screens.", ""));
		
	}
	
	private void createExamplePanels() {
		logLines.add(new InstallLogLine("Panel", "Creating the example panels", "Creating some example panels."));
		
		
		logLines.add(new InstallLogLine("Panel", "Loading Screen 'Home'", "Getting screen 'Home' object from the database."));
		Screen homeScreen = screenService.findBySafeName("Home");
		if(homeScreen == null) {
			logLines.add(new InstallLogLine("Panel", "Error loading 'Home'", "Skip creating panels."));
		}
		
		
		// now create some panels
		Panel panelA = new Panel();
		panelA.setName("Example panel A");
		panelA.setSafeName("Example_panel_A");
		panelA.setScreen(homeScreen);
		panelA.setRowStart(1);
		panelA.setColumnStart(1);
		panelA.setWidth(2);
		panelA.setHeight(2);
		
		Panel panelB = new Panel();
		panelB.setName("Example panel B");
		panelB.setSafeName("Example_panel_B");
		panelB.setScreen(homeScreen);
		panelB.setRowStart(1);
		panelB.setColumnStart(3);
		panelB.setWidth(2);
		panelB.setHeight(1);
		
		Panel panelC = new Panel();
		panelC.setName("Example panel C");
		panelC.setSafeName("Example_panel_C");
		panelC.setScreen(homeScreen);
		panelC.setRowStart(3);
		panelC.setColumnStart(1);
		panelC.setWidth(8);
		panelC.setHeight(2);
		
		List<Panel> panels = new ArrayList<>();
		panels.add(panelA);
		panels.add(panelB);
		panels.add(panelC);
		
		for(Panel panel : panels) {
			// check if panel already exists
			if(panelService.findBySafeName(panel.getSafeName()) != null) {
				logLines.add(new InstallLogLine("Panel", "Panel already exists.", String.format("Content Type: '%s'", panel.getName())));
				continue;
			}
			// does not exits, save
			
			panelService.save(panel);
			String logLine = String.format("Name: '%s'.%n"
					+ "Safe name: '%s'.%n"
					+ "Screen: '%s'.%n"
					+ "Start row: %d.%n"
					+ "Start column: %d.%n"
					+ "Heighth: %d.%n"
					+ "Width: %d.",
					panel.getName(),
					panel.getSafeName(),
					panel.getScreen().getName(),
					panel.getRowStart(),
					panel.getColumnStart(),
					panel.getHeight(),
					panel.getWidth());
			
			logLines.add(new InstallLogLine("Panel", "Panel created", logLine));
			
		}
		
		logLines.add(new InstallLogLine("Panel", "All panels created", ""));
		
		
	}
	
	
	
	
}

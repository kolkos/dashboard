package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.DeviceTypeConfig;
import nl.kolkos.dashboard.entities.DeviceTypeLookup;
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
	
	@Autowired
	private DeviceTypeLookupService deviceTypeLookupService;
	
	@Autowired
	private DeviceTypeConfigService deviceTypeConfigService;
	
	private List<InstallLogLine> logLines = new ArrayList<>();
	
	
	
	public List<InstallLogLine> installDefaultData() {
		logLines = new ArrayList<>();
		logLines.add(new InstallLogLine("General", "Starting installation", ""));
		
		this.createDashboards();
		this.createScreens();
		this.createExamplePanels();
		this.createLookupConfig();

		
		
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
	
	private void createLookupConfig() {
		
		List<DeviceTypeConfig> deviceTypeConfigs = new ArrayList<>();
		
		
		// create the DeviceTypeConfig elements
		
		
		/*
		 * Basic on and off switch
		 */
		DeviceTypeConfig onOff = new DeviceTypeConfig();
		onOff.setDimmer(false);
		onOff.setStaticDevice(false);
		onOff.setName("Switch");
		onOff.setGetStatusTemplateUrl("json.htm?type=devices&rid={idx}");
		onOff.setSetStatusTemplateUrl("json.htm?type=command&param=switchlight&idx={idx}&switchcmd=Toggle");
		onOff.setSetLevelTemplateUrl(null);
		onOff.setIconStatusJsonField("Status");
		onOff.setIcon("fas fa-lightbulb");
		
		deviceTypeConfigs.add(onOff);
		
		
		/*
		 * Dimmer, basically a switch with a level option
		 */
		DeviceTypeConfig dimmer = new DeviceTypeConfig();
		dimmer.setDimmer(true);
		dimmer.setStaticDevice(false);
		dimmer.setName("Dimmer");
		dimmer.setGetStatusTemplateUrl("json.htm?type=devices&rid={idx}");
		dimmer.setSetStatusTemplateUrl("json.htm?type=command&param=switchlight&idx={idx}&switchcmd=Toggle");
		dimmer.setSetLevelTemplateUrl("json.htm?type=command&param=switchlight&idx={idx}&switchcmd=Set%20Level&level={level}");
		dimmer.setIconStatusJsonField("Status");
		dimmer.setIcon("fas fa-lightbulb");
		
		deviceTypeConfigs.add(dimmer);
		
		
		/*
		 * Contact sensor
		 */
		DeviceTypeConfig contact = new DeviceTypeConfig();
		contact.setDimmer(false);
		contact.setStaticDevice(false);
		contact.setName("Contact");
		contact.setGetStatusTemplateUrl("json.htm?type=devices&rid={idx}");
		contact.setSetStatusTemplateUrl(null);
		contact.setSetLevelTemplateUrl(null);
		contact.setIconStatusJsonField("Status");
		contact.setIcon("fas fa-exchange-alt");
		
		deviceTypeConfigs.add(contact);
		
		/*
		 * The following configuration is for all the Utility devices
		 * These are all static devices
		 * The data we wish to display can be configured on panel level
		 */
		DeviceTypeConfig utility = new DeviceTypeConfig();
		utility.setDimmer(false);
		utility.setStaticDevice(true);
		utility.setName("Utility");
		utility.setGetStatusTemplateUrl("json.htm?type=devices&rid={idx}");
		utility.setSetStatusTemplateUrl(null);
		utility.setSetLevelTemplateUrl(null);
		utility.setIconStatusJsonField(null);
		utility.setIcon("fas fa-tachometer-alt");
		
		deviceTypeConfigs.add(utility);
		
		
		/*
		 * The following configuration is for all the temperature devices
		 * Again these are all static devices
		 */
		DeviceTypeConfig temperature = new DeviceTypeConfig();
		temperature.setDimmer(false);
		temperature.setStaticDevice(true);
		temperature.setName("Temperature");
		temperature.setGetStatusTemplateUrl("json.htm?type=devices&rid={idx}");
		temperature.setSetStatusTemplateUrl(null);
		temperature.setSetLevelTemplateUrl(null);
		temperature.setIconStatusJsonField(null);
		temperature.setIcon("fas fa-thermometer-half");
		
		deviceTypeConfigs.add(temperature);
		
		
		DeviceTypeConfig scene = new DeviceTypeConfig();
		scene.setDimmer(false);
		scene.setStaticDevice(false);
		scene.setName("Scene");
		scene.setGetStatusTemplateUrl("json.htm?type=scenes&rid={idx}");
		scene.setSetStatusTemplateUrl("json.htm?type=command&param=switchscene&idx={idx}&switchcmd=On");
		scene.setSetLevelTemplateUrl(null);
		scene.setIconStatusJsonField("Status");
		scene.setIcon("fas fa-object-group");
		
		deviceTypeConfigs.add(scene);
		
		DeviceTypeConfig group = new DeviceTypeConfig();
		group.setDimmer(false);
		group.setStaticDevice(false);
		group.setName("Group");
		group.setGetStatusTemplateUrl("json.htm?type=scenes&rid={idx}");
		group.setSetStatusTemplateUrl("json.htm?type=command&param=switchscene&idx={idx}&switchcmd=Toggle");
		group.setSetLevelTemplateUrl(null);
		group.setIconStatusJsonField("Status");
		group.setIcon("fas fa-object-group");
		
		deviceTypeConfigs.add(group);
		
		
		/*
		 * Finally we add a Unknown type to use if no type has been found
		 * This is a static device
		 */
		DeviceTypeConfig unknown = new DeviceTypeConfig();
		unknown.setDimmer(false);
		unknown.setStaticDevice(true);
		unknown.setName("UNKNOWN");
		unknown.setGetStatusTemplateUrl("json.htm?type=devices&rid={idx}");
		unknown.setSetStatusTemplateUrl(null);
		unknown.setSetLevelTemplateUrl(null);
		unknown.setIconStatusJsonField(null);
		unknown.setIcon("fas fa-question-circle");
		
		deviceTypeConfigs.add(unknown);
		
		
		/*
		 * Now create the lookup table
		 * This table will be used to find the right config for a device
		 */
		List<DeviceTypeLookup> deviceTypeLookups = new ArrayList<>();
		
		DeviceTypeLookup onOffLookup = new DeviceTypeLookup("Lighting", "SwitchType", "On/Off", onOff);
		DeviceTypeLookup dimmerLookup = new DeviceTypeLookup("Lighting", "SwitchType", "Dimmer", dimmer);
		DeviceTypeLookup contactLookup = new DeviceTypeLookup("Lighting", "SwitchType", "Door Contact", contact);
		DeviceTypeLookup smartMeterLookup = new DeviceTypeLookup("P1 Smart Meter", "SubType", "Energy", utility);
		DeviceTypeLookup generalLookup = new DeviceTypeLookup("General", "Type", "General", utility);
		DeviceTypeLookup tempLookup = new DeviceTypeLookup("Temp", "Type", "Temp", temperature);
		DeviceTypeLookup tempHumidityLookup = new DeviceTypeLookup("Temp + Humidity", "Type", "Temp + Humidity", temperature);
		DeviceTypeLookup sceneLookup = new DeviceTypeLookup("Scene", "Type", "Scene", scene);
		DeviceTypeLookup groupLookup = new DeviceTypeLookup("Group", "Type", "Group", group);
		
		DeviceTypeLookup unknownLookup = new DeviceTypeLookup("UNKNOWN", "Type", "UNKNOWN", unknown);
		
		deviceTypeLookups.add(onOffLookup);
		deviceTypeLookups.add(dimmerLookup);
		deviceTypeLookups.add(contactLookup);
		deviceTypeLookups.add(smartMeterLookup);
		deviceTypeLookups.add(generalLookup);
		deviceTypeLookups.add(tempLookup);
		deviceTypeLookups.add(tempHumidityLookup);
		deviceTypeLookups.add(unknownLookup);
		deviceTypeLookups.add(sceneLookup);
		deviceTypeLookups.add(groupLookup);
		
		// now save the lookups
		for(DeviceTypeLookup deviceTypeLookup : deviceTypeLookups) {
			// check of the device type already exists
			if(deviceTypeLookupService.checkIfDeviceTypeLookupExists(deviceTypeLookup)) {
				logLines.add(new InstallLogLine("DeviceTypeLookup", "The DeviceTypeLookup already exists", String.format("%s: %s", deviceTypeLookup.getType(), deviceTypeLookup.getSubTypeValue())));
				continue;
			}
			
			/*
			 * Apparently the lookup does not exist yet
			 * Check if the underlying configuration exits
			 */
			DeviceTypeConfig deviceTypeConfig = deviceTypeLookup.getDeviceTypeConfig();
			
			if(! deviceTypeConfigService.checkIfDeviceTypeConfigExists(deviceTypeConfig)) {
				// save the configuration
				deviceTypeConfigService.save(deviceTypeConfig);
				
				String logLine = String.format("Name: '%s'.%n"
						+ "Icon: '%s'.%n"
						+ "Device is dimmer: %s.%n"
						+ "Device is static: %s.%n"
						+ "Get Status Template URL: '%s'.%n"
						+ "Set Status Template URL: '%s'.%n"
						+ "Set Level Template URL: '%s'.%n"
						+ "JSON field for Icon Status: '%s'.%n", 
						deviceTypeConfig.getName(),
						deviceTypeConfig.getIcon(),
						deviceTypeConfig.isDimmer(),
						deviceTypeConfig.isStaticDevice(),
						deviceTypeConfig.getGetStatusTemplateUrl(),
						deviceTypeConfig.getSetStatusTemplateUrl(),
						deviceTypeConfig.getSetLevelTemplateUrl(),
						deviceTypeConfig.getIconStatusJsonField());
				
				logLines.add(new InstallLogLine("DeviceTypeConfig", "DeviceTypeConfig created", logLine));
			}
			
			// finally save the lookup entry
			deviceTypeLookupService.save(deviceTypeLookup);
			
			// create the log line
			String logLine = String.format("Type: '%s'.%n"
					+ "SubType field: '%s'.%n"
					+ "SubType value: '%s'.%n"
					+ "Configuration: '%s'.%n",
					deviceTypeLookup.getType(),
					deviceTypeLookup.getSubTypeField(),
					deviceTypeLookup.getSubTypeValue(),
					deviceTypeLookup.getDeviceTypeConfig().getName());
			
			
			logLines.add(new InstallLogLine("DeviceTypeLookup", "DeviceTypeLookup created", logLine));
			
			
			
		}
		
		logLines.add(new InstallLogLine("DeviceTypeLookup", "All DeviceTypeLookups created", ""));
		
	}
	
	
	
	
}

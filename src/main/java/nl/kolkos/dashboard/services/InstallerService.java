package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.ContentType;
import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.objects.InstallLogLine;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.objects.SubDeviceType;

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
	private ContentTypeService contentTypeService;
	
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	private List<InstallLogLine> logLines = new ArrayList<>();
	
	
	
	public List<InstallLogLine> installDefaultData() {
		logLines = new ArrayList<>();
		logLines.add(new InstallLogLine("General", "Starting installation", ""));
		
		this.createDashboards();
		this.createScreens();
		this.createContentTypes();
		this.createExamplePanels();
		this.createDeviceConfig();
		
		
		for(InstallLogLine logLine : logLines) {
			System.out.println(logLine.toString());
		}
		
		
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
		homeScreen.setLocation(0);
		homeScreen.setDashboard(defaultDashboard);
		screens.add(homeScreen);
				
		Screen livingRoomScreen = new Screen();
		livingRoomScreen.setName("Living room");
		livingRoomScreen.setSafeName("Living_room");
		livingRoomScreen.setIcon("fas fa-glass-martini");
		livingRoomScreen.setLocation(1);
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
					screen.getLocation(),
					screen.getDashboard().getName());
			
			screenService.save(screen);
			
			logLines.add(new InstallLogLine("Screen", "Screen created", screenLine));
			
		}
		
		
		logLines.add(new InstallLogLine("Screen", "Finished creating screens.", ""));
		
	}
	
	private void createContentTypes() {
		logLines.add(new InstallLogLine("Content Types", "Creating the content types", "Creating the content types."));
		
		// create the content types
		ContentType clock = new ContentType("Clock");
		ContentType html = new ContentType("HTML");
		ContentType domoticzDevice= new ContentType("Device");
		ContentType domoticzChart= new ContentType("Chart");
		
		// add to the list for easier saving		
		List<ContentType> contentTypes = new ArrayList<>();
		contentTypes.add(clock);
		contentTypes.add(html);
		contentTypes.add(domoticzDevice);
		contentTypes.add(domoticzChart);
		
		for(ContentType contentType : contentTypes) {
			// check if the contetType already exists
			if(contentTypeService.findByName(contentType.getName()) != null) {
				logLines.add(new InstallLogLine("Content Types", "Content Type already exists.", String.format("Content Type: '%s'", contentType.getName())));
				continue;
			}
			// does not exist, create
			contentTypeService.save(contentType);
			logLines.add(new InstallLogLine("Content Types", "Content type saved.", String.format("Name: '%s'.", contentType.getName())));
		}
		
		logLines.add(new InstallLogLine("Content Types", "All Content Types created", ""));
	}
	
	private void createExamplePanels() {
		logLines.add(new InstallLogLine("Panel", "Creating the example panels", "Creating some example panels."));
		
		logLines.add(new InstallLogLine("Panel", "Loading Content Type 'clock'", "Getting content type 'clock' object from the database."));
		ContentType clock = contentTypeService.findByName("Clock");
		if(clock == null) {
			logLines.add(new InstallLogLine("Panel", "Error loading 'clock'", "Skip creating panels."));
			return;
		}
		
		logLines.add(new InstallLogLine("Panel", "Loading Content Type 'Device'", "Getting content type 'device' object from the database."));
		ContentType domoticzDevice = contentTypeService.findByName("Device");
		if(domoticzDevice == null) {
			logLines.add(new InstallLogLine("Panel", "Error loading 'Device'", "Skip creating panels."));
			return;
		}
		
		logLines.add(new InstallLogLine("Panel", "Loading Screen 'Home'", "Getting screen 'Home' object from the database."));
		Screen homeScreen = screenService.findBySafeName("Home");
		if(homeScreen == null) {
			logLines.add(new InstallLogLine("Panel", "Error loading 'Home'", "Skip creating panels."));
		}
		
		
		// now create some panels
		Panel panelA = new Panel();
		panelA.setName("Example panel A");
		panelA.setPanelId("Example_panel_A");
		panelA.setContentType(clock);
		panelA.setScreen(homeScreen);
		panelA.setRowStart(1);
		panelA.setColumnStart(1);
		panelA.setWidth(2);
		panelA.setHeight(2);
		
		Panel panelB = new Panel();
		panelB.setName("Example panel B");
		panelB.setPanelId("Example_panel_B");
		panelB.setContentType(domoticzDevice);
		panelB.setScreen(homeScreen);
		panelB.setRowStart(1);
		panelB.setColumnStart(3);
		panelB.setWidth(2);
		panelB.setHeight(1);
		
		Panel panelC = new Panel();
		panelC.setName("Example panel C");
		panelC.setPanelId("Example_panel_C");
		panelC.setContentType(domoticzDevice);
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
			if(panelService.findByPanelId(panel.getPanelId()) != null) {
				logLines.add(new InstallLogLine("Panel", "Panel already exists.", String.format("Content Type: '%s'", panel.getName())));
				continue;
			}
			// does not exits, save
			
			panelService.save(panel);
			String logLine = String.format("Name: '%s'.%n"
					+ "Panel ID: '%s'.%n"
					+ "Content Type: '%s'.%n"
					+ "Screen: '%s'.%n"
					+ "Start row: %d.%n"
					+ "Start column: %d.%n"
					+ "Heighth: %d.%n"
					+ "Width: %d.",
					panel.getName(),
					panel.getPanelId(),
					panel.getContentType().getName(),
					panel.getScreen().getName(),
					panel.getRowStart(),
					panel.getColumnStart(),
					panel.getHeight(),
					panel.getWidth());
			
			logLines.add(new InstallLogLine("Panel", "Panel created", logLine));
			
		}
		
		logLines.add(new InstallLogLine("Panel", "All panels created", ""));
		
		
	}
	
	private void createDeviceConfig() {
		logLines.add(new InstallLogLine("Domoticz Configuration", "Start creating domoticz configuration", "Create Device Types and SubDevice Types."));
		
		List<DeviceType> deviceTypes = new ArrayList<>();
		List<SubDeviceType> subDeviceTypes = new ArrayList<>();
		
		DeviceType lighting = new DeviceType();
		lighting.setDeviceType("Lighting");
		lighting.setSubDeviceField("SwitchType");
		deviceTypes.add(lighting);
		
		// create the sub devices for lighting
		SubDeviceType onOff = new SubDeviceType();
		onOff.setDeviceType(lighting);
		onOff.setSubDeviceType("On/Off");
		onOff.setIcon("fas fa-lightbulb");
		onOff.setTemplatePage("switch");
		
		SubDeviceType dimmer = new SubDeviceType();
		dimmer.setDeviceType(lighting);
		dimmer.setSubDeviceType("Dimmer");
		dimmer.setIcon("fas fa-lightbulb");
		dimmer.setTemplatePage("dimmer");
		
		SubDeviceType contact = new SubDeviceType();
		contact.setDeviceType(lighting);
		contact.setSubDeviceType("Contact");
		contact.setIcon("fab fa-nintendo-switch");
		contact.setTemplatePage("contact");
		contact.setStaticDevice(true);
		
		// add the sub device types to the list
		subDeviceTypes.add(onOff);
		subDeviceTypes.add(dimmer);
		subDeviceTypes.add(contact);
		
		
		// create the group type
		DeviceType group = new DeviceType();
		group.setDeviceType("Group");
		group.setSubDeviceField("Type");
		deviceTypes.add(group);
		
		// create the SINGLE sub device for group
		SubDeviceType groupSubDevice = new SubDeviceType();
		groupSubDevice.setDeviceType(group);
		groupSubDevice.setSubDeviceType("Group");
		groupSubDevice.setIcon("fas fa-object-group");
		groupSubDevice.setTemplatePage("group");
		subDeviceTypes.add(groupSubDevice);
		
		
		// create the scene type
		DeviceType scene = new DeviceType();
		scene.setDeviceType("Scene");
		scene.setSubDeviceField("Type");
		deviceTypes.add(scene);
		
		// create the SINGLE sub device for scene
		SubDeviceType sceneSubDevice = new SubDeviceType();
		sceneSubDevice.setDeviceType(scene);
		sceneSubDevice.setSubDeviceType("Scene");
		sceneSubDevice.setIcon("far fa-object-group");
		sceneSubDevice.setTemplatePage("scene");
		subDeviceTypes.add(sceneSubDevice);
		
		
		// create the p1 meter type
		DeviceType p1meter = new DeviceType();
		p1meter.setDeviceType("P1 Smart Meter");
		p1meter.setSubDeviceField("Type");
		deviceTypes.add(p1meter);
		
		SubDeviceType p1SubDevice = new SubDeviceType();
		p1SubDevice.setDeviceType(p1meter);
		p1SubDevice.setSubDeviceType("P1 Smart Meter");
		p1SubDevice.setIcon("fas fa-tachometer-alt");
		p1SubDevice.setTemplatePage("p1");
		p1SubDevice.setStaticDevice(true);
		subDeviceTypes.add(p1SubDevice);
		
		
		// create the temperature type
		DeviceType temp = new DeviceType();
		temp.setDeviceType("Temp");
		temp.setSubDeviceField("Type");
		deviceTypes.add(temp);
		
		SubDeviceType tempSubDevice = new SubDeviceType();
		tempSubDevice.setDeviceType(temp);
		tempSubDevice.setSubDeviceType("Temp");
		tempSubDevice.setIcon("fas fa-thermometer-half");
		tempSubDevice.setTemplatePage("temp");
		tempSubDevice.setStaticDevice(true);
		subDeviceTypes.add(tempSubDevice);

		
		// create the temperature + humiditu type
		DeviceType tempHumidity = new DeviceType();
		tempHumidity.setDeviceType("Temp + Humidity");
		tempHumidity.setSubDeviceField("Type");
		deviceTypes.add(tempHumidity);
		
		SubDeviceType tempHumiditySubDevice = new SubDeviceType();
		tempHumiditySubDevice.setDeviceType(tempHumidity);
		tempHumiditySubDevice.setSubDeviceType("Temp + Humidity");
		tempHumiditySubDevice.setIcon("fas fa-thermometer-half");
		tempHumiditySubDevice.setTemplatePage("tempHumidity");
		tempHumiditySubDevice.setStaticDevice(true);
		subDeviceTypes.add(tempHumiditySubDevice);

		
		// create the temperature + humiditu type
		DeviceType general = new DeviceType();
		general.setDeviceType("General");
		general.setSubDeviceField("Type");
		deviceTypes.add(general);
		
		SubDeviceType generalSubDevice = new SubDeviceType();
		generalSubDevice.setDeviceType(general);
		generalSubDevice.setSubDeviceType("General");
		generalSubDevice.setIcon("fas fa-info-circle");
		generalSubDevice.setTemplatePage("general");
		generalSubDevice.setStaticDevice(true);
		subDeviceTypes.add(generalSubDevice);

		
		
		// first loop through the Device Types to create them
		for(DeviceType deviceType : deviceTypes) {
			// check if Device Type already exists
			if(deviceTypeService.findByDeviceType(deviceType.getDeviceType()) != null) {
				// device type already exists
				logLines.add(new InstallLogLine("Device Type", "Device Type already exists.", String.format("Device Type: '%s'", deviceType.getDeviceType())));
				continue;
			}
			// does not exists, save it
			deviceTypeService.save(deviceType);
			logLines.add(new InstallLogLine("SubDevice Type", "Device Type saved", String.format("'%s' with SubDevice Types saved.", deviceType.getDeviceType())));
		}
		
		// now create the sub device types
		for(SubDeviceType subDeviceType : subDeviceTypes) {
			// check if sub device already exists
			if(subDeviceTypeService.findBySubDeviceType(subDeviceType.getSubDeviceType()) != null) {
				// subdevice type already exists
				logLines.add(new InstallLogLine("SubDevice Type", "SubDevice Type already exists", String.format("SubDevice Type '%s'.", subDeviceType.getSubDeviceType())));
				continue;
			}
			// does not exists
			// create log line for the sub device types
			String logLineSubDeviceType = String.format("SubDevice Type: '%s'.%n"
					+ "Device Type: '%s'.%n"
					+ "Icon: '%s'.%n"
					+ "Template page: '%s'.%n"
					+ "Static device: %s.",
					subDeviceType.getSubDeviceType(),
					subDeviceType.getDeviceType().getDeviceType(),
					subDeviceType.getIcon(),
					subDeviceType.getTemplatePage(),
					subDeviceType.isStaticDevice());
			
			subDeviceTypeService.save(subDeviceType);
			
			logLines.add(new InstallLogLine("SubDevice Type", "Creating SubDevice Type", logLineSubDeviceType));
			
		}
		
		
		
		logLines.add(new InstallLogLine("Device Type", "All Device Types created", ""));
	}
	
}

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
	
	private List<InstallLogLine> logLines = new ArrayList<>();
	
	
	
	public List<InstallLogLine> installDefaultData() {
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
				
	}
	
	private void createScreens() {
		logLines.add(new InstallLogLine("Screen", "Creating some screens", "Creating a couple of default screens."));
		
		logLines.add(new InstallLogLine("Screen", "Loading the dashboard 'Default'", ""));
		Dashboard defaultDashboard = dashboardService.findBySafeName("Default");
		if(defaultDashboard == null) {
			logLines.add(new InstallLogLine("Screen", "Error loading default dashboard", "Dashboard could not be found! Skip creating screens..."));
			return;
		}
		
		logLines.add(new InstallLogLine("Screen", "Creating dashboard 'Home'", ""));
		
		Screen homeScreen = new Screen();
		homeScreen.setName("Home");
		homeScreen.setSafeName(screenService.createSafeName(homeScreen.getName()));
		homeScreen.setIcon("fas fa-home");
		homeScreen.setLocation(0);
		homeScreen.setDashboard(defaultDashboard);
		screenService.saveNewScreen(homeScreen);
		
		String screenLine = String.format("Screen name: '%s'.%n"
				+ "Safe name: '%s'.%n"
				+ "Icon: '%s'.%n"
				+ "Location: %d.%n"
				+ "Dashboard: '%s'.", 
				homeScreen.getName(),
				homeScreen.getSafeName(),
				homeScreen.getIcon(),
				homeScreen.getLocation(),
				homeScreen.getDashboard().getName());
		
		logLines.add(new InstallLogLine("Screen", "Screen created", screenLine));
		
		
		logLines.add(new InstallLogLine("Screen", "Creating dashboard 'Living room'", ""));
		
		Screen livingRoomScreen = new Screen();
		livingRoomScreen.setName("Living room");
		livingRoomScreen.setSafeName(screenService.createSafeName(livingRoomScreen.getName()));
		livingRoomScreen.setIcon("fas fa-glass-martini");
		livingRoomScreen.setLocation(1);
		livingRoomScreen.setDashboard(defaultDashboard);
		screenService.saveNewScreen(livingRoomScreen);
		
		screenLine = String.format("Screen name: '%s'.%n"
				+ "Safe name: '%s'.%n"
				+ "Icon: '%s'.%n"
				+ "Location: %d.%n"
				+ "Dashboard: '%s'.", 
				livingRoomScreen.getName(),
				livingRoomScreen.getSafeName(),
				livingRoomScreen.getIcon(),
				livingRoomScreen.getLocation(),
				livingRoomScreen.getDashboard().getName());
		
		logLines.add(new InstallLogLine("Screen", "Screen created", screenLine));
		
		logLines.add(new InstallLogLine("Screen", "All screens created", ""));
		
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
		panelA.setPanelId(panelService.createPanelId(panelA.getName()));
		panelA.setContentType(clock);
		panelA.setScreen(homeScreen);
		panelA.setRowStart(1);
		panelA.setColumnStart(1);
		panelA.setWidth(2);
		panelA.setHeight(2);
		
		Panel panelB = new Panel();
		panelB.setName("Example panel B");
		panelB.setPanelId(panelService.createPanelId(panelB.getName()));
		panelB.setContentType(domoticzDevice);
		panelB.setScreen(homeScreen);
		panelB.setRowStart(1);
		panelB.setColumnStart(3);
		panelB.setWidth(2);
		panelB.setHeight(1);
		
		Panel panelC = new Panel();
		panelC.setName("Example panel C");
		panelC.setPanelId(panelService.createPanelId(panelC.getName()));
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
		
		DeviceType lighting = new DeviceType();
		lighting.setDeviceType("Lighting");
		lighting.setSubDeviceField("SwitchType");
		
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
		
		List<SubDeviceType> lightingSubDevices = new ArrayList<>();
		lightingSubDevices.add(onOff);
		lightingSubDevices.add(dimmer);
		lightingSubDevices.add(contact);
		lighting.setSubDeviceTypes(lightingSubDevices);
		
		deviceTypes.add(lighting);
		
		// create the group type
		DeviceType group = new DeviceType();
		group.setDeviceType("Group");
		group.setSubDeviceField("Type");
		
		// create the SINGLE sub device for group
		SubDeviceType groupSubDevice = new SubDeviceType();
		groupSubDevice.setDeviceType(group);
		groupSubDevice.setSubDeviceType("Group");
		groupSubDevice.setIcon("fas fa-object-group");
		groupSubDevice.setTemplatePage("group");
		
		List<SubDeviceType> groupSubDevices = new ArrayList<>();
		groupSubDevices.add(groupSubDevice);
		group.setSubDeviceTypes(groupSubDevices);
		
		deviceTypes.add(group);
		
		
		// create the scene type
		DeviceType scene = new DeviceType();
		scene.setDeviceType("Scene");
		scene.setSubDeviceField("Type");
		
		// create the SINGLE sub device for group
		SubDeviceType sceneSubDevice = new SubDeviceType();
		sceneSubDevice.setDeviceType(scene);
		sceneSubDevice.setSubDeviceType("Scene");
		sceneSubDevice.setIcon("far fa-object-group");
		sceneSubDevice.setTemplatePage("scene");
		
		List<SubDeviceType> sceneSubDevices = new ArrayList<>();
		sceneSubDevices.add(sceneSubDevice);
		scene.setSubDeviceTypes(sceneSubDevices);
		
		deviceTypes.add(scene);
		
		// create the p1 meter type
		DeviceType p1meter = new DeviceType();
		p1meter.setDeviceType("P1 Smart Meter");
		p1meter.setSubDeviceField("Type");
		
		// create the SINGLE sub device for group
		SubDeviceType p1SubDevice = new SubDeviceType();
		p1SubDevice.setDeviceType(p1meter);
		p1SubDevice.setSubDeviceType("P1 Smart Meter");
		p1SubDevice.setIcon("fas fa-tachometer-alt");
		p1SubDevice.setTemplatePage("p1");
		p1SubDevice.setStaticDevice(true);
		
		List<SubDeviceType> p1SubDevices = new ArrayList<>();
		p1SubDevices.add(p1SubDevice);
		p1meter.setSubDeviceTypes(p1SubDevices);
		
		deviceTypes.add(p1meter);
		
		// create the temperature type
		DeviceType temp = new DeviceType();
		temp.setDeviceType("Temp");
		temp.setSubDeviceField("Type");
		
		// create the SINGLE sub device for group
		SubDeviceType tempSubDevice = new SubDeviceType();
		tempSubDevice.setDeviceType(temp);
		tempSubDevice.setSubDeviceType("Temp");
		tempSubDevice.setIcon("fas fa-thermometer-half");
		tempSubDevice.setTemplatePage("temp");
		tempSubDevice.setStaticDevice(true);
		
		List<SubDeviceType> tempSubDevices = new ArrayList<>();
		tempSubDevices.add(tempSubDevice);
		temp.setSubDeviceTypes(tempSubDevices);
		
		deviceTypes.add(temp);
		
		// create the temperature + humiditu type
		DeviceType tempHumidity = new DeviceType();
		tempHumidity.setDeviceType("Temp + Humidity");
		tempHumidity.setSubDeviceField("Type");
		
		// create the SINGLE sub device for group
		SubDeviceType tempHumiditySubDevice = new SubDeviceType();
		tempHumiditySubDevice.setDeviceType(tempHumidity);
		tempHumiditySubDevice.setSubDeviceType("Temp + Humidity");
		tempHumiditySubDevice.setIcon("fas fa-thermometer-half");
		tempHumiditySubDevice.setTemplatePage("tempHumidity");
		tempHumiditySubDevice.setStaticDevice(true);
		
		List<SubDeviceType> tempHumiditySubDevices = new ArrayList<>();
		tempHumiditySubDevices.add(tempHumiditySubDevice);
		tempHumidity.setSubDeviceTypes(tempHumiditySubDevices);
		
		deviceTypes.add(tempHumidity);
		
		
		// create the temperature + humiditu type
		DeviceType general = new DeviceType();
		general.setDeviceType("General");
		general.setSubDeviceField("Type");
		
		// create the SINGLE sub device for group
		SubDeviceType generalSubDevice = new SubDeviceType();
		generalSubDevice.setDeviceType(general);
		generalSubDevice.setSubDeviceType("General");
		generalSubDevice.setIcon("fas fa-info-circle");
		generalSubDevice.setTemplatePage("general");
		generalSubDevice.setStaticDevice(true);
		
		List<SubDeviceType> generalSubDevices = new ArrayList<>();
		generalSubDevices.add(generalSubDevice);
		general.setSubDeviceTypes(generalSubDevices);
		
		deviceTypes.add(general);
		
		// now loop to save the device types
		for(DeviceType deviceType : deviceTypes) {
			// create log line for the device type
			String logLineDeviceType = String.format("Device Type: '%s'.%n"
					+ "Field to determine subdevice: '%s'.%n", deviceType.getDeviceType(), deviceType.getSubDeviceField());
			// now loop through the sub device types
			logLines.add(new InstallLogLine("Device Type", "Creating Device Type", logLineDeviceType));
			for(SubDeviceType subDeviceType : deviceType.getSubDeviceTypes()) {
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
				logLines.add(new InstallLogLine("SubDevice Type", "Creating SubDevice Type", logLineSubDeviceType));
			}
			deviceTypeService.save(deviceType);
			logLines.add(new InstallLogLine("SubDevice Type", "Device Type saved", String.format("'%s' with SubDevice Types saved.", deviceType.getDeviceType())));
		}
		
		logLines.add(new InstallLogLine("Device Type", "All Device Types created", ""));
	}
	
}

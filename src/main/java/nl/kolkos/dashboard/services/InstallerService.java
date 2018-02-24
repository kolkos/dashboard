package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.ContentType;
import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.DeviceType;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeConfig;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;
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
		//this.createDeviceConfig();
		
		this.createLights();
		this.createGroup();
		this.createScene();
		this.createSmartMeter();
		this.createTemp();
		this.createTempHumidity();
		this.createGeneral();
		
		
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
	
	public void createLights() {
		/*
		 * On the first step I'll create light (devices)
		 */
		
		/*
		 * Create the (Main) Device Type
		 */
		DeviceType lighting = new DeviceType();
		lighting.setDeviceType("Lighting");
		lighting.setSubDeviceField("SwitchType");
		
		/*
		 * Create the sub device types
		 */
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
		
		/*
		 * Create the sub device configurations
		 */
		SubDeviceTypeConfig onOffConfig = new SubDeviceTypeConfig();
		onOffConfig.setSubDeviceType(onOff);
		onOffConfig.setIconStatusField("Status");
		onOffConfig.setIconClickAction("Toggle");
		
		SubDeviceTypeConfig dimmerConfig = new SubDeviceTypeConfig();
		dimmerConfig.setSubDeviceType(dimmer);
		dimmerConfig.setIconStatusField("Status");
		dimmerConfig.setIconClickAction("Toggle");
		dimmerConfig.setSliderMinValue(0);
		dimmerConfig.setSliderMaxValueField("MaxDimLevel");
		dimmerConfig.setSliderStepValue(1);
		dimmerConfig.setSliderCurrentValueField("LevelInt");
		
		SubDeviceTypeConfig contactConfig = new SubDeviceTypeConfig();
		contactConfig.setSubDeviceType(contact);
		contactConfig.setIconStatusField("Status");
		
		/*
		 * Finally create the status fields for the sub devices
		 */
		SubDeviceTypeStatusField onOffStatusField1 = new SubDeviceTypeStatusField();
		onOffStatusField1.setSubDeviceTypeConfig(onOffConfig);
		onOffStatusField1.setStatusField("Status");
		onOffStatusField1.setLabel("Status");
		List<SubDeviceTypeStatusField> onOffStatusFields = new ArrayList<>();
		onOffStatusFields.add(onOffStatusField1);
		
		SubDeviceTypeStatusField dimmerStatusField1 = new SubDeviceTypeStatusField();
		dimmerStatusField1.setSubDeviceTypeConfig(dimmerConfig);
		dimmerStatusField1.setStatusField("Status");
		dimmerStatusField1.setLabel("Status");
		List<SubDeviceTypeStatusField> dimmerStatusFields = new ArrayList<>();
		dimmerStatusFields.add(dimmerStatusField1);
		
		SubDeviceTypeStatusField contactStatusField1 = new SubDeviceTypeStatusField();
		contactStatusField1.setSubDeviceTypeConfig(contactConfig);
		contactStatusField1.setStatusField("Status");
		contactStatusField1.setLabel("Status");
		List<SubDeviceTypeStatusField> contactStatusFields = new ArrayList<>();
		contactStatusFields.add(contactStatusField1);
		
		/*
		 * create the reversed relations
		 */
		// Config <- Fields
		onOffConfig.setSubDeviceTypeStatusFields(onOffStatusFields);
		dimmerConfig.setSubDeviceTypeStatusFields(dimmerStatusFields);
		contactConfig.setSubDeviceTypeStatusFields(contactStatusFields);
		
		// SubDeviceType <- Config
		onOff.setSubDeviceTypeConfig(onOffConfig);
		dimmer.setSubDeviceTypeConfig(dimmerConfig);
		contact.setSubDeviceTypeConfig(contactConfig);
		
		// DeviceType <- SubDeviceTypes
		lighting.setSubDeviceTypes(lightingSubDevices);
		
		// save the device
		saveDeviceType(lighting);
		
	}
	
	public void createGroup() {
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
		
		SubDeviceTypeConfig groupConfig = new SubDeviceTypeConfig();
		groupConfig.setSubDeviceType(groupSubDevice);
		groupConfig.setIconStatusField("Status");
		groupConfig.setIconClickAction("Toggle");
		
		
		SubDeviceTypeStatusField groupStatusField1 = new SubDeviceTypeStatusField();
		groupStatusField1.setSubDeviceTypeConfig(groupConfig);
		groupStatusField1.setStatusField("Status");
		groupStatusField1.setLabel("Status");
		List<SubDeviceTypeStatusField> groupStatusFields = new ArrayList<>();
		groupStatusFields.add(groupStatusField1);
		
		/*
		 * Backwards relations
		 */
		groupConfig.setSubDeviceTypeStatusFields(groupStatusFields);
		
		// SubDeviceType <- Config
		groupSubDevice.setSubDeviceTypeConfig(groupConfig);

		
		// DeviceType <- SubDeviceTypes
		group.setSubDeviceTypes(groupSubDevices);
		
		// save the device
		saveDeviceType(group);
		

	}
	
	public void createScene() {
		// create the scene type
		DeviceType scene = new DeviceType();
		scene.setDeviceType("Scene");
		scene.setSubDeviceField("Type");
		
		// create the SINGLE sub device for scene
		SubDeviceType sceneSubDevice = new SubDeviceType();
		sceneSubDevice.setDeviceType(scene);
		sceneSubDevice.setSubDeviceType("Scene");
		sceneSubDevice.setIcon("far fa-object-group");
		sceneSubDevice.setTemplatePage("scene");
		
		
		List<SubDeviceType> sceneSubDevices = new ArrayList<>();
		sceneSubDevices.add(sceneSubDevice);
		
		SubDeviceTypeConfig sceneConfig = new SubDeviceTypeConfig();
		sceneConfig.setSubDeviceType(sceneSubDevice);
		sceneConfig.setIconStatusField("Status");
		sceneConfig.setIconClickAction("On");
		
		
		SubDeviceTypeStatusField sceneStatusField1 = new SubDeviceTypeStatusField();
		sceneStatusField1.setSubDeviceTypeConfig(sceneConfig);
		sceneStatusField1.setStatusField("Status");
		sceneStatusField1.setLabel("Status");
		List<SubDeviceTypeStatusField> sceneStatusFields = new ArrayList<>();
		sceneStatusFields.add(sceneStatusField1);
		
		/*
		 * Backwards relations
		 */
		sceneConfig.setSubDeviceTypeStatusFields(sceneStatusFields);
		
		// SubDeviceType <- Config
		sceneSubDevice.setSubDeviceTypeConfig(sceneConfig);

		
		// DeviceType <- SubDeviceTypes
		scene.setSubDeviceTypes(sceneSubDevices);
		
		// save the device
		saveDeviceType(scene);
		
	}
	
	public void createSmartMeter() {
		// create the p1 meter type
		DeviceType p1meter = new DeviceType();
		p1meter.setDeviceType("P1 Smart Meter");
		p1meter.setSubDeviceField("Type");
		
		SubDeviceType p1SubDevice = new SubDeviceType();
		p1SubDevice.setDeviceType(p1meter);
		p1SubDevice.setSubDeviceType("P1 Smart Meter");
		p1SubDevice.setIcon("fas fa-tachometer-alt");
		p1SubDevice.setTemplatePage("p1");
		p1SubDevice.setStaticDevice(true);
		
		
		List<SubDeviceType> smartMeterSubDevices = new ArrayList<>();
		smartMeterSubDevices.add(p1SubDevice);
		
		SubDeviceTypeConfig smartMeterConfig = new SubDeviceTypeConfig();
		smartMeterConfig.setSubDeviceType(p1SubDevice);
		
		
		SubDeviceTypeStatusField smartMeterStatusField1 = new SubDeviceTypeStatusField();
		smartMeterStatusField1.setSubDeviceTypeConfig(smartMeterConfig);
		smartMeterStatusField1.setStatusField("Usage");
		SubDeviceTypeStatusField smartMeterStatusField2 = new SubDeviceTypeStatusField();
		smartMeterStatusField2.setSubDeviceTypeConfig(smartMeterConfig);
		smartMeterStatusField2.setStatusField("CounterToday");
		List<SubDeviceTypeStatusField> sceneStatusFields = new ArrayList<>();
		sceneStatusFields.add(smartMeterStatusField1);
		sceneStatusFields.add(smartMeterStatusField2);
		
		/*
		 * Backwards relations
		 */
		smartMeterConfig.setSubDeviceTypeStatusFields(sceneStatusFields);
		
		// SubDeviceType <- Config
		p1SubDevice.setSubDeviceTypeConfig(smartMeterConfig);

		
		// DeviceType <- SubDeviceTypes
		p1meter.setSubDeviceTypes(smartMeterSubDevices);
		
		// save the device
		saveDeviceType(p1meter);
		
	}
	
	public void createTemp() {
		// create the temperature type
		DeviceType temp = new DeviceType();
		temp.setDeviceType("Temp");
		temp.setSubDeviceField("Type");
		
		SubDeviceType tempSubDevice = new SubDeviceType();
		tempSubDevice.setDeviceType(temp);
		tempSubDevice.setSubDeviceType("Temp");
		tempSubDevice.setIcon("fas fa-thermometer-half");
		tempSubDevice.setTemplatePage("temp");
		tempSubDevice.setStaticDevice(true);
		
		List<SubDeviceType> tempSubDevices = new ArrayList<>();
		tempSubDevices.add(tempSubDevice);
		
		SubDeviceTypeConfig tempConfig = new SubDeviceTypeConfig();
		tempConfig.setSubDeviceType(tempSubDevice);
		
		
		SubDeviceTypeStatusField tempStatusField1 = new SubDeviceTypeStatusField();
		tempStatusField1.setSubDeviceTypeConfig(tempConfig);
		tempStatusField1.setStatusField("Temp");
		tempStatusField1.setLabel("Temperature");
		List<SubDeviceTypeStatusField> tempStatusFields = new ArrayList<>();
		tempStatusFields.add(tempStatusField1);
		
		/*
		 * Backwards relations
		 */
		tempConfig.setSubDeviceTypeStatusFields(tempStatusFields);
		
		// SubDeviceType <- Config
		tempSubDevice.setSubDeviceTypeConfig(tempConfig);

		
		// DeviceType <- SubDeviceTypes
		temp.setSubDeviceTypes(tempSubDevices);
		
		// save the device
		saveDeviceType(temp);
		
	}
	
	public void createTempHumidity() {
		// create the temperature + humiditu type
		DeviceType tempHumidity = new DeviceType();
		tempHumidity.setDeviceType("Temp + Humidity");
		tempHumidity.setSubDeviceField("Type");

		
		SubDeviceType tempHumiditySubDevice = new SubDeviceType();
		tempHumiditySubDevice.setDeviceType(tempHumidity);
		tempHumiditySubDevice.setSubDeviceType("Temp + Humidity");
		tempHumiditySubDevice.setIcon("fas fa-thermometer-half");
		tempHumiditySubDevice.setTemplatePage("tempHumidity");
		tempHumiditySubDevice.setStaticDevice(true);

		
		
		List<SubDeviceType> tempHumiditySubDevices = new ArrayList<>();
		tempHumiditySubDevices.add(tempHumiditySubDevice);
		
		SubDeviceTypeConfig tempHumidityConfig = new SubDeviceTypeConfig();
		tempHumidityConfig.setSubDeviceType(tempHumiditySubDevice);
		
		
		SubDeviceTypeStatusField tempHumidityStatusField1 = new SubDeviceTypeStatusField();
		tempHumidityStatusField1.setSubDeviceTypeConfig(tempHumidityConfig);
		tempHumidityStatusField1.setStatusField("Temp");
		tempHumidityStatusField1.setLabel("Temperature");
		SubDeviceTypeStatusField tempHumidityStatusField2 = new SubDeviceTypeStatusField();
		tempHumidityStatusField2.setSubDeviceTypeConfig(tempHumidityConfig);
		tempHumidityStatusField2.setStatusField("Humidity");
		tempHumidityStatusField2.setLabel("Humidity");
		List<SubDeviceTypeStatusField> tempStatusFields = new ArrayList<>();
		tempStatusFields.add(tempHumidityStatusField1);
		tempStatusFields.add(tempHumidityStatusField2);
		
		/*
		 * Backwards relations
		 */
		tempHumidityConfig.setSubDeviceTypeStatusFields(tempStatusFields);
		
		// SubDeviceType <- Config
		tempHumiditySubDevice.setSubDeviceTypeConfig(tempHumidityConfig);

		
		// DeviceType <- SubDeviceTypes
		tempHumidity.setSubDeviceTypes(tempHumiditySubDevices);
		
		// save the device
		saveDeviceType(tempHumidity);
		
	}
	
	public void createGeneral() {
		// create the temperature + humiditu type
		DeviceType general = new DeviceType();
		general.setDeviceType("General");
		general.setSubDeviceField("Type");

		
		SubDeviceType generalSubDevice = new SubDeviceType();
		generalSubDevice.setDeviceType(general);
		generalSubDevice.setSubDeviceType("General");
		generalSubDevice.setIcon("fas fa-info-circle");
		generalSubDevice.setTemplatePage("general");
		generalSubDevice.setStaticDevice(true);
		
		
		List<SubDeviceType> generalSubDevices = new ArrayList<>();
		generalSubDevices.add(generalSubDevice);
		
		SubDeviceTypeConfig generalConfig = new SubDeviceTypeConfig();
		generalConfig.setSubDeviceType(generalSubDevice);
		
		
		SubDeviceTypeStatusField generalStatusField1 = new SubDeviceTypeStatusField();
		generalStatusField1.setSubDeviceTypeConfig(generalConfig);
		generalStatusField1.setStatusField("Data");
		generalStatusField1.setLabel("Status");

		List<SubDeviceTypeStatusField> generalStatusFields = new ArrayList<>();
		generalStatusFields.add(generalStatusField1);

		/*
		 * Backwards relations
		 */
		generalConfig.setSubDeviceTypeStatusFields(generalStatusFields);
		
		// SubDeviceType <- Config
		generalSubDevice.setSubDeviceTypeConfig(generalConfig);

		
		// DeviceType <- SubDeviceTypes
		general.setSubDeviceTypes(generalSubDevices);
		
		// save the device
		saveDeviceType(general);
		
	}
	
	public void saveDeviceType(DeviceType deviceType) {
		if(deviceTypeService.findByDeviceType(deviceType.getDeviceType()) != null) {
			// device type already exists
			logLines.add(new InstallLogLine("Device Type", "Device Type already exists.", String.format("Device Type: '%s'", deviceType.getDeviceType())));
			return;
		}
		
		String logMessage = "";
		logMessage += String.format("DeviceType: '%s'%n", deviceType.getDeviceType());
		logMessage += String.format("SubDeviceType field: '%s'%n", deviceType.getSubDeviceField());
		// loop through the SubDeviceTypes
		for(SubDeviceType subDeviceTpe : deviceType.getSubDeviceTypes()) {
			logMessage += String.format("  SubDeviceType:%n");
			logMessage += String.format("    SubDeviceType: '%s'%n", subDeviceTpe.getSubDeviceType());
			logMessage += String.format("    Icon: '%s'%n", subDeviceTpe.getIcon());
			logMessage += String.format("    Template: '%s'%n", subDeviceTpe.getTemplatePage());
			logMessage += String.format("    Static device: '%s'%n", subDeviceTpe.isStaticDevice());
			logMessage += String.format("    Config:%n");
			// config
			logMessage += String.format("      Icon status field: '%s'%n", subDeviceTpe.getSubDeviceTypeConfig().getIconStatusField());
			logMessage += String.format("      Icon click action: '%s'%n", subDeviceTpe.getSubDeviceTypeConfig().getIconClickAction());
			logMessage += String.format("      Slider min value: '%d'%n", subDeviceTpe.getSubDeviceTypeConfig().getSliderMinValue());
			logMessage += String.format("      Slider max value field: '%s'%n", subDeviceTpe.getSubDeviceTypeConfig().getSliderMaxValueField());
			logMessage += String.format("      Slider value step: '%d'%n", subDeviceTpe.getSubDeviceTypeConfig().getSliderStepValue());
			logMessage += String.format("      Slider current value field: '%s'%n", subDeviceTpe.getSubDeviceTypeConfig().getSliderCurrentValueField());
			// config fields
			for(SubDeviceTypeStatusField subDeviceTypeStatusField : subDeviceTpe.getSubDeviceTypeConfig().getSubDeviceTypeStatusFields()) {
				logMessage += String.format("      Fields:%n");
				logMessage += String.format("        Domoticz Status field: '%s'%n", subDeviceTypeStatusField.getStatusField());
				logMessage += String.format("        Label: '%s'%n", subDeviceTypeStatusField.getLabel());
			}
			
		}
		
		deviceTypeService.save(deviceType);
		logLines.add(new InstallLogLine("Domoticz Configuration", String.format("saved device type '%s'", deviceType.getDeviceType()), logMessage));
	}
	
	
}

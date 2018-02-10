package nl.kolkos.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import nl.kolkos.dashboard.configurations.DomoticzConfiguration;
import nl.kolkos.dashboard.objects.ContentType;
import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.objects.SubDeviceType;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DeviceTypeService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;


@SpringBootApplication
@EnableConfigurationProperties(DomoticzConfiguration.class)
public class DashboardApplication {
	@Autowired
	private ContentTypeService contentTypeService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private ScreenService screenService;
		
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private DomoticzConfiguration domoticzConfig;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
            
            this.testContent();
            this.createDeviceConfig();
            
            

        };
        
    }
	
	public void testContent() {
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

		
		contentTypeService.save(contentTypes);
		
		// create a Dashboard
		Dashboard defaultDashboard = new Dashboard();
		defaultDashboard.setName("Default");
		dashboardService.save(defaultDashboard);
		
		// create a few screens
		Screen homeScreen = new Screen();
		homeScreen.setName("Home");
		homeScreen.setIcon("fas fa-home");
		homeScreen.setLocation(0);
		homeScreen.setDashboard(defaultDashboard);
		
		Screen livingRoomScreen = new Screen();
		livingRoomScreen.setName("Living room");
		livingRoomScreen.setIcon("fas fa-glass-martini");
		livingRoomScreen.setLocation(1);
		livingRoomScreen.setDashboard(defaultDashboard);
		
		screenService.save(homeScreen);
		screenService.save(livingRoomScreen);
		
		
		// now create some panels
		Panel panelA = new Panel();
		panelA.setName("Initial panel A");
		panelA.setScreen(homeScreen);
		panelA.setRowStart(1);
		panelA.setColumnStart(1);
		panelA.setWidth(2);
		panelA.setHeight(2);
		
		Panel panelB = new Panel();
		panelB.setName("Initial panel B");
		panelB.setScreen(homeScreen);
		panelB.setRowStart(1);
		panelB.setColumnStart(3);
		panelB.setWidth(2);
		panelB.setHeight(1);
		
		Panel panelC = new Panel();
		panelC.setName("Initial panel C");
		panelC.setScreen(homeScreen);
		panelC.setRowStart(3);
		panelC.setColumnStart(1);
		panelC.setWidth(8);
		panelC.setHeight(1);
		
		try {
			panelService.save(panelA);
			panelService.save(panelB);
			panelService.save(panelC);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void createDeviceConfig() {
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
		
		deviceTypeService.save(lighting);
		
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
		
		deviceTypeService.save(group);
		
		
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
		
		deviceTypeService.save(scene);
		
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
		
		deviceTypeService.save(p1meter);
		
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
		
		deviceTypeService.save(temp);
		
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
		
		deviceTypeService.save(tempHumidity);
		
		
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
		
		deviceTypeService.save(general);
		
	}
	

}

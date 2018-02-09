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

import nl.kolkos.dashboard.configurations.AvailableDeviceTypeConfiguration;
import nl.kolkos.dashboard.configurations.DeviceType;
import nl.kolkos.dashboard.configurations.DomoticzConfiguration;
import nl.kolkos.dashboard.objects.ContentType;
import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
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
	private AvailableDeviceTypeConfiguration availableDeviceTypeConfiguration;
	

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
            
            List<DeviceType> deviceTypes = availableDeviceTypeConfiguration.getDeviceTypes();
            for(DeviceType deviceType : deviceTypes) {
            		System.out.println(deviceType.getName());
            }
            
            

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
	

}

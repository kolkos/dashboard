package nl.kolkos.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import nl.kolkos.dashboard.objects.ContentType;
import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DeviceTypeService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;


@SpringBootApplication
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
            this.domoticzContent();

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
		homeScreen.setIcon("fa fa-home");
		homeScreen.setLocation(0);
		homeScreen.setDashboard(defaultDashboard);
		
		Screen livingRoomScreen = new Screen();
		livingRoomScreen.setName("Living room");
		livingRoomScreen.setIcon("fa fa-glass");
		livingRoomScreen.setLocation(1);
		livingRoomScreen.setDashboard(defaultDashboard);
		
		screenService.save(homeScreen);
		screenService.save(livingRoomScreen);
		
		
		// now create some panels
		Panel panelA = new Panel();
		panelA.setName("Initial panel A");
		panelA.setContentType(clock);
		panelA.setScreen(homeScreen);
		panelA.setRowStart(1);
		panelA.setColumnStart(1);
		panelA.setWidth(2);
		panelA.setHeight(2);
		
		Panel panelB = new Panel();
		panelB.setName("Initial panel B");
		panelB.setContentType(domoticzDevice);
		panelB.setScreen(homeScreen);
		panelB.setRowStart(1);
		panelB.setColumnStart(3);
		panelB.setWidth(2);
		panelB.setHeight(1);
		
		try {
			panelService.save(panelA);
			panelService.save(panelB);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void domoticzContent() {
		DeviceType light = new DeviceType();
		light.setName("light");
		light.setType("devices");
		light.setFilter("light");
		light.setOrderBy("Name");
		light.setSubTypeField("SwitchType");
				
		
		DeviceType weather = new DeviceType();
		weather.setName("weather");
		weather.setType("devices");
		weather.setFilter("weather");
		weather.setOrderBy("Name");
		weather.setSubTypeField("SwitchType");
		
		
		DeviceType temp = new DeviceType();
		temp.setName("temp");
		temp.setType("devices");
		temp.setFilter("temp");
		temp.setOrderBy("Name");
		temp.setSubTypeField("Type");
		
		
		DeviceType utility = new DeviceType();
		utility.setName("utility");
		utility.setType("devices");
		utility.setFilter("utility");
		utility.setOrderBy("Name");
		utility.setSubTypeField("Type");
		
		
		deviceTypeService.save(light);
		deviceTypeService.save(weather);
		deviceTypeService.save(temp);
		deviceTypeService.save(utility);
		
		
	}
}

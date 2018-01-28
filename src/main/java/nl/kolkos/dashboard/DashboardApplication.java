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
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Row;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.RowService;
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
	private RowService rowService;
	
	@Autowired
	private PanelService panelService;

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

        };
    }
	
	public void testContent() {
		// create the content types
		ContentType clock = new ContentType("Clock");
		ContentType html = new ContentType("HTML");
		ContentType domoticzSwitch = new ContentType("Domoticz: Switch");
		ContentType domoticzDimmer = new ContentType("Domoticz: Dimmer");
		ContentType domoticzGroup = new ContentType("Domoticz: Group");
		ContentType domoticzScene = new ContentType("Domoticz: Scene");
		ContentType domoticzWeather = new ContentType("Domoticz: Weather");
		ContentType domoticzTemp = new ContentType("Domoticz: Temperature");
		ContentType domoticzTempChart = new ContentType("Domoticz: Weather chart");
		ContentType domoticzUtility = new ContentType("Domoticz: Utility");
		ContentType domoticzUtilityChart = new ContentType("Domoticz: Utility chart");
		
		// add to the list for easier saving		
		List<ContentType> contentTypes = new ArrayList<>();
		contentTypes.add(clock);
		contentTypes.add(html);
		contentTypes.add(domoticzSwitch);
		contentTypes.add(domoticzDimmer);
		contentTypes.add(domoticzGroup);
		contentTypes.add(domoticzScene);
		contentTypes.add(domoticzWeather);
		contentTypes.add(domoticzTemp);
		contentTypes.add(domoticzTempChart);
		contentTypes.add(domoticzUtility);
		contentTypes.add(domoticzUtilityChart);
		
		contentTypeService.save(contentTypes);
		
		// create a Dashboard
		Dashboard defaultDashboard = new Dashboard();
		defaultDashboard.setName("Default");
		dashboardService.save(defaultDashboard);
		
		// create a few screens
		Screen homeScreen = new Screen();
		homeScreen.setName("Home");
		homeScreen.setIcon("house");
		homeScreen.setLocation(0);
		homeScreen.setDashboard(defaultDashboard);
		
		Screen livingRoomScreen = new Screen();
		livingRoomScreen.setName("Living room");
		livingRoomScreen.setIcon("house");
		livingRoomScreen.setLocation(1);
		livingRoomScreen.setDashboard(defaultDashboard);
		
		screenService.save(homeScreen);
		screenService.save(livingRoomScreen);
		
		// create some rows
		Row homeScreenRow0 = new Row();
		homeScreenRow0.setPosition(0);
		homeScreenRow0.setScreen(homeScreen);
		
		Row homeScreenRow1 = new Row();
		homeScreenRow1.setPosition(1);
		homeScreenRow1.setScreen(homeScreen);
		
		Row livingRoomScreenRow0 = new Row();
		livingRoomScreenRow0.setPosition(0);
		livingRoomScreenRow0.setScreen(livingRoomScreen);
		
		rowService.save(homeScreenRow0);
		rowService.save(homeScreenRow1);
		rowService.save(livingRoomScreenRow0);
		
		// now create the panels
		Panel panelA = new Panel();
		panelA.setRow(homeScreenRow0);
		panelA.setTitle("Panel A");
		panelA.setPosition(0);
		panelA.setWidth(2);
		
		Panel panelB = new Panel();
		panelB.setRow(homeScreenRow0);
		panelB.setTitle("Panel B");
		panelB.setPosition(1);
		panelB.setWidth(6);
		
		Panel panelC = new Panel();
		panelC.setRow(homeScreenRow1);
		panelC.setTitle("Panel C");
		panelC.setPosition(0);
		panelC.setWidth(8);
		
		panelService.save(panelA);
		panelService.save(panelB);
		panelService.save(panelC);
		
		
	}
}

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
import nl.kolkos.dashboard.objects.Row;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DeviceTypeService;
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
		
		// create some rows
		Row homeScreenRow0 = new Row();
		homeScreenRow0.setPosition(1);
		homeScreenRow0.setScreen(homeScreen);
		
		Row homeScreenRow1 = new Row();
		homeScreenRow1.setPosition(2);
		homeScreenRow1.setScreen(homeScreen);
		
		Row homeScreenRow2 = new Row();
		homeScreenRow2.setPosition(3);
		homeScreenRow2.setScreen(homeScreen);
				
		Row livingRoomScreenRow0 = new Row();
		livingRoomScreenRow0.setPosition(1);
		livingRoomScreenRow0.setScreen(livingRoomScreen);
		
		rowService.save(homeScreenRow0);
		rowService.save(homeScreenRow1);
		rowService.save(homeScreenRow2);
		rowService.save(livingRoomScreenRow0);
		
		// now create the panels
		Panel panelA = new Panel();
		panelA.setRow(homeScreenRow0);
		panelA.setTitle("Panel A");
		panelA.setPosition(0);
		panelA.setWidth(2);
		panelA.setContentType(clock);
		panelA.setShowTitle(false);
		
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
		
		Panel panelD = new Panel();
		panelD.setRow(homeScreenRow2);
		panelD.setTitle("Panel D");
		panelD.setPosition(0);
		panelD.setWidth(1);
		
		Panel panelE = new Panel();
		panelE.setRow(homeScreenRow2);
		panelE.setTitle("Panel E");
		panelE.setPosition(1);
		panelE.setWidth(5);
		
		Panel panelF = new Panel();
		panelF.setRow(homeScreenRow2);
		panelF.setTitle("Panel F");
		panelF.setPosition(2);
		panelF.setWidth(2);
		
		
		panelService.save(panelA);
		panelService.save(panelB);
		panelService.save(panelC);
		panelService.save(panelD);
		panelService.save(panelE);
		panelService.save(panelF);
		
		
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

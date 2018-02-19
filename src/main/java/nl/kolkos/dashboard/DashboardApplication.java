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
import org.springframework.scheduling.annotation.EnableScheduling;

import nl.kolkos.dashboard.configurations.DomoticzConfiguration;
import nl.kolkos.dashboard.objects.ContentDevice;
import nl.kolkos.dashboard.objects.ContentType;
import nl.kolkos.dashboard.objects.Dashboard;
import nl.kolkos.dashboard.objects.Device;
import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.objects.SubDeviceType;
import nl.kolkos.dashboard.repositories.SubDeviceTypeRepository;
import nl.kolkos.dashboard.services.ContentDeviceService;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DeviceService;
import nl.kolkos.dashboard.services.DeviceTypeService;
import nl.kolkos.dashboard.services.DomoticzSyncService;
import nl.kolkos.dashboard.services.InstallerService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;
import nl.kolkos.dashboard.services.SubDeviceTypeService;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(DomoticzConfiguration.class)
public class DashboardApplication {
	
	
	
	
	@Autowired
	private ScreenService screenService;
		
	@Autowired
	private PanelService panelService;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private ContentDeviceService contentDeviceService;
	
	@Autowired
	private InstallerService installerService;

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
            
            //installerService.installDefaultData();
            
           // this.testContent();
           // this.createDeviceConfig();
            
            //this.createTestDevicePanels();

        };
        
    }
	
	private void testContent() {
		
		
		
		
		
		
				
	}
	
	
	
//	private void createTestDevicePanels() {
//		Dashboard dashboard = dashboardService.findBySafeName("Default");
//		Screen screen = screenService.getScreen("Home", dashboard);
//		Panel panel = panelService.findPanelByPanelIdAndScreen("Initial_panel_A", screen);
//		
//		SubDeviceType subDeviceType = subDeviceTypeService.findBySubDeviceType("Dimmer");
//		Device device = deviceService.findBySubDeviceTypeAndIdx(subDeviceType, 9);
//		
//		
//		ContentDevice contentDevice1 = new ContentDevice();
//		contentDevice1.setPanel(panel);
//		contentDevice1.setDevice(device);
//		
//		contentDeviceService.save(contentDevice1);
//		
//	}
	

}

package nl.kolkos.dashboard;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import nl.kolkos.dashboard.configurations.DomoticzConfiguration;


@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(DomoticzConfiguration.class)
public class DashboardApplication {


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
	

	

}

package nl.kolkos.dashboard.scheduledtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import nl.kolkos.dashboard.services.DomoticzSyncService;

@Component
@ComponentScan(basePackages = { "nl.kolkos.dashboard.*" })
public class ScheduledTasks {
	@Autowired
	private DomoticzSyncService domoticzSyncService;
	
	
	@Scheduled(fixedRate = 60_000, initialDelay = 60_000)
    public void syncDevices() {
        // use the service
		
	
    }
	
}

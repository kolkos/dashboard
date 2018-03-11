package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.DeviceTypeConfig;
import nl.kolkos.dashboard.repositories.DeviceTypeConfigRepository;

@Service
public class DeviceTypeConfigService {
	@Autowired
	private DeviceTypeConfigRepository deviceTypeConfigRepository;
	
	public DeviceTypeConfig findByName(String name) {
		return deviceTypeConfigRepository.findByName(name);
	}
	
	public boolean checkIfDeviceTypeConfigExists(DeviceTypeConfig deviceTypeConfig) {
		boolean exists = true;
		
		if(this.findByName(deviceTypeConfig.getName()) == null) {
			exists = false;
		}
		
		return exists;
	}
	
	public void save(DeviceTypeConfig deviceTypeConfig) {
		deviceTypeConfigRepository.save(deviceTypeConfig);
	}
}

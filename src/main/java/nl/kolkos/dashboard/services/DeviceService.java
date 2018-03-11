package nl.kolkos.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.repositories.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;

	
	public void save(Device device) {
		deviceRepository.save(device);
		
	}
	
	public List<Device> findAllDevices(){
		return deviceRepository.findAllByOrderByNameAsc();
	}
	
}

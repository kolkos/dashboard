package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.repositories.DeviceTypeRepository;

@Service
public class DeviceTypeService {
	@Autowired
	private DeviceTypeRepository deviceTypeRepository;
	
	public void save(DeviceType deviceType) {
		deviceTypeRepository.save(deviceType);
	}
}

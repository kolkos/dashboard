package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.DeviceTypeLookup;
import nl.kolkos.dashboard.repositories.DeviceTypeLookupRepository;

@Service
public class DeviceTypeLookupService {
	@Autowired
	private DeviceTypeLookupRepository deviceTypeLookupRepository;
	
	public DeviceTypeLookup findByTypeAndSubTypeFieldAndSubTypeValue(String type, String subTypeField, String subTypeValue) {
		return deviceTypeLookupRepository.findByTypeAndSubTypeFieldAndSubTypeValue(type, subTypeField, subTypeValue);
	}
	
	public boolean checkIfDeviceTypeLookupExists(DeviceTypeLookup deviceTypeLookup) {
		boolean exists = true; 
		
		if(this.findByTypeAndSubTypeFieldAndSubTypeValue(deviceTypeLookup.getType(), deviceTypeLookup.getSubTypeField(), deviceTypeLookup.getSubTypeValue()) == null) {
			exists = false;
		}
		
		
		return exists;
	}
	
	
	public void save(DeviceTypeLookup deviceTypeLookup) {
		deviceTypeLookupRepository.save(deviceTypeLookup);
	}
	
}

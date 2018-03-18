package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.DeviceTypeConfig;
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
	
	
	public String getSubTypeFieldForType(String type) {
		// get a DeviceTypeLookup for this type
		DeviceTypeLookup deviceTypeLookup = deviceTypeLookupRepository.findFirstByType(type);
		
		// nog get this subtype field
		return deviceTypeLookup.getSubTypeField();
	}
	
	public DeviceTypeConfig getDeviceTypeConfig(String type, String subTypeField, String subTypeValue) {
		DeviceTypeLookup deviceTypeLookup = this.findByTypeAndSubTypeFieldAndSubTypeValue(type, subTypeField, subTypeValue);
		// check if a result is found, if not use the UNKNOWN type
		if(deviceTypeLookup == null) {
			// not found
			// get the unknown device
			DeviceTypeLookup unknown = this.findByTypeAndSubTypeFieldAndSubTypeValue("UNKNOWN", "Type", "UNKNOWN");
			return unknown.getDeviceTypeConfig();
		}
		
		
		return deviceTypeLookup.getDeviceTypeConfig();
	}
	
}

package nl.kolkos.dashboard.services;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DevicePanel;
import nl.kolkos.dashboard.entities.DeviceTypeConfig;
import nl.kolkos.dashboard.repositories.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private DeviceTypeLookupService deviceTypeLookupService;
	
	@Autowired
	private DevicePanelService devicePanelService;

	
	public void save(Device device) {
		deviceRepository.save(device);
		
	}
	
	public List<Device> findAllDevices(){
		return deviceRepository.findAllByOrderByNameAsc();
	}
	
	public Device findById(long id) {
		return deviceRepository.findById(id);
	}
	
	
	/**
	 * This method will do the following:
	 * 	1. Determine the value of the field 'Type'
	 * 	2. Based on the value of 'Type' it will get the SubDevice Field from the Lookup
	 * 		a. the SubDevice Field could be another field in the JSON result
	 * 		b. Or it is the field 'Type' again
	 * 	3. The value of the SubDevice Field will now be determined
	 * 	4. Based on the value of the Type and SubDeviceField a DeviceTypeConfig will be determined
	 * 	5. With the 'idx' and the Configuration the method will check if the Device is already registrerd
	 *  6. Check if a device has been found:
	 *  		a. if a device is found, check if the name is changed
	 *  		b. update the name if necessary
	 *  		c. return this device
	 *  7. ctreate a new device object
	 *  
	 * @param jsonDevice
	 * @return
	 * @throws JSONException
	 */
	public Device jsonToDevice(JSONObject jsonDevice) throws JSONException {
		
		/*
		 * Get the configuration for this device
		 */
		// 1. Get the Type
		String type = jsonDevice.getString("Type");
		// replace the number in Lighting
		type = type.replaceAll("Lighting \\d{1,}", "Lighting");
		
		// 2. Get the SubType Field for this Type
		String subTypeField = deviceTypeLookupService.getSubTypeFieldForType(type);
		
		// 3. Get the value of this sub type field
		String subTypeValue = jsonDevice.getString(subTypeField);
		
		// 4. Get the config for these values
		DeviceTypeConfig config = deviceTypeLookupService.getDeviceTypeConfig(type, subTypeField, subTypeValue);
		
		
		// 5. check if the device is already registered in the database
		int idx = jsonDevice.getInt("idx");
		Device device = deviceRepository.findByIdxAndDeviceTypeConfig(idx, config);
		
		// 6. check if the device has been found
		String name = jsonDevice.getString("Name");
		if(device != null) {
			// a device has been found
			// check if the name is still the same
			if(! device.getName().equals(name)) {
				// update the name
				device.setName(name);
				// save the device
				deviceRepository.save(device);
			}
			
			// set the device as registered
			device.setDeviceRegistered(true);
			
			return device;
		}
		
		// device is not registered
		device = new Device();
		
		// fill the device properties
		device.setIdx(idx);
		device.setName(name);
		device.setDeviceTypeConfig(config);
		device.setDeviceRegistered(false);
		
		
		return device;
	}
	
	public void removeDevice(Device device) {
		// get the device panels where this device is linked to
		List<DevicePanel> devicePanels = devicePanelService.findByDevice(device);
		
		// loop through the DevicePanels and delete them
		for(DevicePanel devicePanel : devicePanels) {
			devicePanelService.delete(devicePanel);
		}
		
		// now delete the device
		deviceRepository.delete(device);
		
	}
	
	
}

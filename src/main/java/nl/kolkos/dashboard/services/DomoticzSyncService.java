package nl.kolkos.dashboard.services;


import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Device;
import nl.kolkos.dashboard.objects.DeviceType;
import nl.kolkos.dashboard.objects.SubDeviceTypeField;
import nl.kolkos.dashboard.objects.SubDeviceType;


@Service
public class DomoticzSyncService {
	@Autowired
	private DomoticzCommunicator domoticz;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	@Autowired
	private SubDeviceTypeFieldService subDeviceFieldService;
	
	@Autowired
	private DeviceService deviceService;
	
	public void syncDevices() {
		// receive the full list of devices
		String path = "type=devices&filter=all&used=true&order=Name";
		String response = domoticz.getDataFromServer(path);
		
		JSONObject jsonObject;
		try {
			jsonObject = domoticz.createJSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			
			for(int i = 0; i < jsonArray.length(); i++) {
				// get the json object
				JSONObject jsonSubObject = jsonArray.getJSONObject(i);
				
				String type = jsonSubObject.getString("Type");
				type = type.replaceAll("Lighting \\d{1,}", "Lighting");
				String name = jsonSubObject.getString("Name");
				int idx = jsonSubObject.getInt("idx");
				
				// get the device type object for this type
				DeviceType deviceType = deviceTypeService.findByDeviceType(type);
				// check if the result isn't empty
				if(deviceType == null) {
					// skip this result
					System.out.println("Unknown device type: " + type);
					continue;
				}
				
				// now we know the field which contains the sub device type
				String subDeviceTypeField = deviceType.getSubDeviceField();
				// check if this field exists
				if(! jsonSubObject.has(subDeviceTypeField)) {
					// skip this result
					System.out.println("The field " + subDeviceTypeField + " has not been found for idx " + idx + " (" + type + ")");
					continue;
				}
				
				String subDeviceTypeValue = jsonSubObject.getString(subDeviceTypeField);
				// get the sub device type
				SubDeviceType subDeviceType = subDeviceTypeService.findBySubDeviceType(subDeviceTypeValue);
				// check if the sub device type has been found
				if(subDeviceType == null) {
					// skip this result
					System.out.println("Unknown sub device type: " + subDeviceTypeValue);
					continue;
				}
				
				// loop through the json fields
				Iterator<?> jsonKeys = jsonSubObject.keys();
				while(jsonKeys.hasNext()) {
					String jsonKey = (String)jsonKeys.next();
					
					// create a new SubDeviceField object
					SubDeviceTypeField subDeviceField = new SubDeviceTypeField();
					subDeviceField.setField(jsonKey);
					subDeviceField.setSubDeviceType(subDeviceType);
					
					// save it
					subDeviceFieldService.save(subDeviceField);
					
				}
				
				// now create a new device
				Device device = new Device();
				device.setIdx(idx);
				device.setSubDeviceType(subDeviceType);
				
				deviceService.save(device);
			
				
				
				

			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

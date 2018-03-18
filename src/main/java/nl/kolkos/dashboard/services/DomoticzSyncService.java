package nl.kolkos.dashboard.services;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Device;


@Service
public class DomoticzSyncService {
	@Autowired
	private DomoticzCommunicator domoticz;
	
	@Autowired
	private DeviceService deviceService;
	
	public List<Device> getDevices() {
 		// receive the full list of devices
 		String path = "type=devices&filter=all&used=true&order=Name";
 		String response = domoticz.getDataFromServer(path);
 		
 		// list for the results
 		List<Device> devices = new ArrayList<>();
 		
 		JSONObject jsonObject;
 		try {
 			jsonObject = domoticz.createJSONObject(response);
 			JSONArray jsonArray = jsonObject.getJSONArray("result");
 			
 			for(int i = 0; i < jsonArray.length(); i++) {
 				// get the json object
 				JSONObject jsonDevice = jsonArray.getJSONObject(i);
 				
 				
 				Device device = deviceService.jsonToDevice(jsonDevice);
 				
 				devices.add(device);
 				
 
 			}
 			
 		} catch (JSONException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		return devices;
 	}
	
}

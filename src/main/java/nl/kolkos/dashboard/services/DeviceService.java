package nl.kolkos.dashboard.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DevicePanel;
import nl.kolkos.dashboard.entities.DeviceType;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.PanelStatusField;
import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;
import nl.kolkos.dashboard.repositories.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;
	
	@Autowired
	private DomoticzCommunicator domoticz;
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@Autowired
	private DevicePanelService devicePanelService;
	
	@Autowired
	private PanelStatusFieldService panelStatusFieldService;
	
	
	public void save(Device device) {
		// check if the device already exists
		if(deviceRepository.findBySubDeviceTypeAndIdx(device.getSubDeviceType(), device.getIdx()) == null) {
			// only save when it does not exist
			System.out.println(String.format("%s: saving '%s' with idx '%d'", new Date(), device.getSubDeviceType().getSubDeviceType(), device.getIdx() ));
			deviceRepository.save(device);
			
			// we are done here
			return;
		}
		
		// now check if the name is changed, if so update this device
		Device deviceInDB = deviceRepository.findBySubDeviceTypeAndIdx(device.getSubDeviceType(), device.getIdx());
		if(! deviceInDB.getName().equals(device.getName())) {
			// update the device in the database
			
			
			System.out.println(String.format("%s: updating the name of device '%d' from '%s' to '%s'.", new Date(), deviceInDB.getId(), deviceInDB.getName(), device.getName() ));
			
			deviceInDB.setName(device.getName());
			deviceRepository.save(deviceInDB);
		}
		
	}
	
	public List<Device> findAllDevices(){
		return deviceRepository.findAllByOrderByNameAsc();
	}
	
	public Device findBySubDeviceTypeAndIdx(SubDeviceType subDeviceType, int ixd) {
		return deviceRepository.findBySubDeviceTypeAndIdx(subDeviceType, ixd);
	}
	
	public List<DevicePanel> getCurrentDeviceInfo() {
		
		// receive the full list of devices
		String path = "type=devices&filter=all&used=true&order=Name";
		String response = domoticz.getDataFromServer(path);
		
		List<DevicePanel> results = new ArrayList<>();
		
		JSONObject jsonObject;
		try {
			jsonObject = domoticz.createJSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			
			for(int i = 0; i < jsonArray.length(); i++) {
				// get the json object
				JSONObject jsonSubObject = jsonArray.getJSONObject(i);
				
				String name = jsonSubObject.getString("Name");
				String type = jsonSubObject.getString("Type");
				type = type.replaceAll("Lighting \\d{1,}", "Lighting");
				int idx = jsonSubObject.getInt("idx");
				
				// get the field to get the sub device type
				DeviceType deviceType = deviceTypeService.findByDeviceType(type);
				String field = deviceType.getSubDeviceField();
				String subDeviceValue = jsonSubObject.getString(field);
				
				// get the sub device configuration
				SubDeviceType subDeviceType = subDeviceTypeService.findBySubDeviceType(subDeviceValue);
				if(subDeviceType == null) {
					System.err.println(String.format("Can't find SubDeviceType '%s'", subDeviceValue));
					continue;
				}
				
				// find the device identified by this idx and SubDeviceType
				Device device = this.findBySubDeviceTypeAndIdx(subDeviceType, idx);
				// check if a device is found
				if(device == null) {
					System.out.println(String.format("Can't find device idx '%d' with SubDeviceType '%s'", idx, subDeviceValue));
					continue;
				}
				
				// check if the device is linked to one or more panels
				List<DevicePanel> devicePanels = devicePanelService.findByDevice(device);
				if(devicePanels.size() < 1) {
					System.out.println("Ignore " + name + " (" + idx + ")");
					continue;
				}
				
				// now loop through the DevicePanels
				for(DevicePanel devicePanel : devicePanels) {
					// get the panel
					Panel panel = devicePanel.getPanel();
					
					// sync (just to be sure)
					panelStatusFieldService.syncPanelFields(panel);
					
					// get the StatusFields
					List<PanelStatusField> panelStatusFields = panel.getPanelStatusFields();
					
					// loop through the status fields
					for(PanelStatusField panelStatusField : panelStatusFields) {
						// get the SubDeviceTypeStatusField for the configuration
						SubDeviceTypeStatusField subDeviceTypeStatusField = panelStatusField.getSubDeviceTypeStatusField();
						String jsonField = subDeviceTypeStatusField.getStatusField();
						
						// check if the field exists
						String value = null;
						if(jsonSubObject.has(jsonField)) {
							value = jsonSubObject.getString(jsonField);
						}
						
						String debugText = String.format("---> Device: '%s', Field: '%s', Value: '%s'", name, jsonField, value);			
						System.out.println(debugText);
						
						// set the value
						panelStatusField.setValue(value);
					}
					
					// add the result to the list
					results.add(devicePanel);
				}
				
				
				
				
				
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
}

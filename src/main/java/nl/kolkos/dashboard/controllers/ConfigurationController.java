package nl.kolkos.dashboard.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.kolkos.dashboard.configurations.DomoticzConfiguration;
import nl.kolkos.dashboard.entities.Dashboard;
import nl.kolkos.dashboard.entities.DeviceType;
import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;
import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.services.ContentTypeService;
import nl.kolkos.dashboard.services.DashboardService;
import nl.kolkos.dashboard.services.DeviceTypeService;
import nl.kolkos.dashboard.services.DomoticzCommunicator;
import nl.kolkos.dashboard.services.DomoticzSyncService;
import nl.kolkos.dashboard.services.PanelService;
import nl.kolkos.dashboard.services.ScreenService;
import nl.kolkos.dashboard.services.SubDeviceTypeService;

@Controller
@RequestMapping(path="/config")
public class ConfigurationController {
		
	@Autowired
	private DomoticzCommunicator domoticz;
	
	@Autowired
	private DeviceTypeService deviceTypeService;
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	@Autowired
	private DomoticzSyncService domoticzSyncService;
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody String testDomoticzConnection() {
		String path = "type=devices&filter=all&used=true&order=Name";
		
		String response = domoticz.getDataFromServer(path);
		String result = "";
		
		List<String> usefullFields = new ArrayList<>();
		usefullFields.add("Data");
		usefullFields.add("Description");
		usefullFields.add("Type");
		
		
		
		HashMap<String, Set<String>> values = new HashMap<>();
		Set<String> allKeys = new TreeSet<>();
				
		
		
		try {
			JSONObject jsonObject = domoticz.createJSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			// loop through the array
			
			for(int i = 0; i < jsonArray.length(); i++) {
				// get the json object
				JSONObject jsonSubObject = jsonArray.getJSONObject(i);
				
				String type = jsonSubObject.has("SwitchType") ? jsonSubObject.getString("SwitchType") : jsonSubObject.getString("Type");
				
				type = type.replaceAll("Lighting \\d{1,2}", "Lighting");
				
				// check if type already exists
				if(! values.containsKey(type)) {
					// create a new list for the keys
					Set<String> keys = new HashSet<>();
					// add to a new hash map entry
					values.put(type, keys);
				}
				
				// now get the list for this type
				Set<String> keys = values.get(type);
				
				// loop through the other fields
				Iterator<?> jsonKeys = jsonSubObject.keys();
				while(jsonKeys.hasNext()) {
					String jsonKey = (String)jsonKeys.next();
					keys.add(jsonKey);
					
					allKeys.add(jsonKey);
				}
				
				

			}
			
			SortedSet<String> types = new TreeSet<String>(values.keySet());
			
			
			result += "<table border='1'>";
			result += "<tr>";
			result += "<td></td>";
			// create top row with device types
			for(String type : types) {
				result += "<td>" + type + "</td>";
			}
			result += "</tr>";
			
			// now loop through the keys
			for(String key : allKeys) {
				result += "<tr>";
				
				// first column is the key name
				result += "<td>" + key + "</td>";
				
				// now loop through the types to check if the key exists
				
				for(String type : types) {
					Set<String> typeFields = values.get(type);

					
					String check = "";
					String bg = "";
					if(typeFields.contains(key)) {
						check = "X";
						bg = " style='background-color: lime;'";
					}
					result += "<td " + bg + ">" + check + "</td>";
				}
				
				result += "</tr>";
			}
			
			
			result += "<table>";
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		
		
		
		
		return result;
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public @ResponseBody String listDevices() {
		String path = "type=devices&filter=all&used=true&order=Name";
		String response = domoticz.getDataFromServer(path);
		
		String result = "";
		
		
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
				String idx = jsonSubObject.getString("idx");
				
				HashMap<String, String> placeHolderReplacements = new HashMap<>();
				placeHolderReplacements.put("\\{idx\\}", idx);
				
				// get the device type for this entry
				DeviceType deviceType = deviceTypeService.findByDeviceType(type);
				
				result += String.format("Device: %s <br/>", idx);
				result += String.format("--Name: %s <br/>", name);
				result += String.format("--Type: %s <br/>", type);
				if(deviceType != null) {
					result += String.format("--Found a <strong>device type</strong> configuration <br/>");
					result += String.format("--Using field '%s' to get the sub device type <br/>", deviceType.getSubDeviceField());
					
					String subDeviceTypeValue = jsonSubObject.getString(deviceType.getSubDeviceField());
					result += String.format("--Using the value '%s' to find the sub device config.<br/>", subDeviceTypeValue);
					
					// get the sub device type
					SubDeviceType subDeviceType = subDeviceTypeService.findBySubDeviceType(subDeviceTypeValue);
					if(subDeviceType != null) {
						result += String.format("--Found a <strong>sub device type</strong> configuration <br/>");
						result += String.format("----Icon:%s<br/>", subDeviceType.getIcon());
						result += String.format("----Template page:%s<br/>", subDeviceType.getTemplatePage());
						
						
					}else {
						result += String.format("--<span style='color: red;'>Could not find a configuration for %s!</span><br/>", subDeviceTypeValue);
					}
					
					
				}else {
					result += String.format("--<span style='color: red;'>Could not find a configuration for %s!</span><br/>", type);
				}
				
				result += "<br/>";
				
				
				

			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result += "<hr>";
		return result;
	}
	
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	public @ResponseBody String syncDomoticz() {
		domoticzSyncService.syncDevices();
		return "Synced";
	}
	
}

package nl.kolkos.dashboard.controllers.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DeviceTypeConfig;
import nl.kolkos.dashboard.services.DeviceService;
import nl.kolkos.dashboard.services.DeviceTypeConfigService;
import nl.kolkos.dashboard.services.DomoticzSyncService;

@Controller
@RequestMapping(path="/config/device")
public class DeviceConfigController {
	
	@Autowired
	private DomoticzSyncService domoticzSyncService;
	
	@Autowired
	private DeviceTypeConfigService deviceTypeConfigService;
	
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public String getAllDomoticzDevices(Model model) {
		
		model.addAttribute("title", "Domoticz Devices");
		model.addAttribute("description", "Select the devices you wish to add to a dashboard");
		
		List<Device> devices = domoticzSyncService.getDevices();
		model.addAttribute("devices", devices);
		
		return "backend/devices_result";
	}
	
	@RequestMapping(value = "/addDevice", method = RequestMethod.GET)
	public String registerDevice(
			@RequestParam("name") String name,
			@RequestParam("idx") int idx,
			@RequestParam("configId") long configId,
			Model model) {
		
		model.addAttribute("title", "Add Domoticz Devices");
		model.addAttribute("description", "Add a Domoticz device to the database");
		
		// create a Device
		Device device = new Device();
		device.setIdx(idx);
		device.setName(name);
		
		// get the configuration
		DeviceTypeConfig deviceTypeConfig = deviceTypeConfigService.findById(configId);
		device.setDeviceTypeConfig(deviceTypeConfig);
		
		// save
		deviceService.save(device);
		
		return "redirect:/config/device/devices";
	}
	
	@RequestMapping(value = "/removeDevice", method = RequestMethod.GET)
	public String removeDevice(
			@RequestParam("id") long id,
			Model model) {
		
		model.addAttribute("title", "Remove Domoticz Device");
		model.addAttribute("description", "Remove the domoticz device from the database.");
		
		// fist get the device
		Device device = deviceService.findById(id);
		if(device == null) {
			model.addAttribute("message", "The device could not be found");
			return "backend/error";
		}
		
		// delete the device
		deviceService.removeDevice(device);
				
		return "redirect:/config/device/devices";
	}
	
	

}

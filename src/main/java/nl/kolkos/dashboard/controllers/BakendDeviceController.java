package nl.kolkos.dashboard.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.kolkos.dashboard.objects.SubDeviceType;
import nl.kolkos.dashboard.objects.SubDeviceTypeField;
import nl.kolkos.dashboard.services.SubDeviceTypeFieldService;
import nl.kolkos.dashboard.services.SubDeviceTypeService;



@Controller
@RequestMapping(path="/config/device")
public class BakendDeviceController {
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	@Autowired
	private SubDeviceTypeFieldService subDeviceTypeFieldService;
	
	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public String showSubDeviceTypes(
			Model model) {
		
		model.addAttribute("title", "Device types");
		model.addAttribute("description", "This screen shows the created device types.");
		
		Iterable<SubDeviceType> subDeviceTypes = subDeviceTypeService.findAll();
		model.addAttribute("subDeviceTypes", subDeviceTypes);
		
		return "backend/device_types_result";
	}
	
	@RequestMapping(value = "/types/fields/{subDeviceTypeId}", method = RequestMethod.GET)
	public String showSubDeviceFields(
			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
			@RequestParam(value = "fieldId", required = false) Long fieldId,
			Model model) {
		
		
		
		// get the sub device type
		SubDeviceType subDeviceType = subDeviceTypeService.findById(subDeviceTypeId);
		if(subDeviceType == null) {
			model.addAttribute("message", "The SubDeviceType could not be found");
			return "backend/error";
		}
		model.addAttribute("subDeviceTypeId", subDeviceTypeId);
		
		model.addAttribute("title", "Device type fields for " + subDeviceType.getSubDeviceType());
		model.addAttribute("description", "Select the field you wish to get the value of.");
		
		// get the fields for this sub device type
		List<SubDeviceTypeField> fields = subDeviceTypeFieldService.findBySubDeviceType(subDeviceType);
		model.addAttribute("fields", fields);
		
		if(fieldId != null) {
			// find the field 
			SubDeviceTypeField subDeviceField = subDeviceTypeFieldService.findById(fieldId);
			if(subDeviceField != null) {
				
				
				subDeviceField.setUseField(!subDeviceField.isUseField());
				
				subDeviceTypeFieldService.save(subDeviceField);
				String redirectUrl = String.format("redirect:/config/device/types/fields/%d", subDeviceTypeId);
				return redirectUrl;
			}
		}
		
		
		return "backend/device_type_fields_result";
	}
	
}

package nl.kolkos.dashboard.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeConfig;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;
import nl.kolkos.dashboard.services.SubDeviceTypeConfigService;
import nl.kolkos.dashboard.services.SubDeviceTypeService;
import nl.kolkos.dashboard.services.SubDeviceTypeStatusFieldService;



@Controller
@RequestMapping(path="/config/device")
public class BakendDeviceController {
	
	@Autowired
	private SubDeviceTypeService subDeviceTypeService;
	
	@Autowired
	private SubDeviceTypeStatusFieldService subDeviceTypeStatusFieldService;
	
	@Autowired
	private SubDeviceTypeConfigService subDeviceTypeConfigService;
	
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
			Model model) {
		
		// get the sub device type
		SubDeviceType subDeviceType = subDeviceTypeService.findById(subDeviceTypeId);
		if(subDeviceType == null) {
			model.addAttribute("message", "The SubDeviceType could not be found");
			return "backend/error";
		}
		
		model.addAttribute("title", "Device type fields for " + subDeviceType.getSubDeviceType());
		model.addAttribute("description", "Select the field you wish to get the value of.");
		
		/*
		 * FORM
		 */
		// create a new SubDeviceTypeStatusField
		SubDeviceTypeStatusField subDeviceStatusField = new SubDeviceTypeStatusField();
		// link the sub device type
		subDeviceStatusField.setSubDeviceType(subDeviceType);
		model.addAttribute("subDeviceStatusField", subDeviceStatusField);
		
		
		/*
		 * RESULTS
		 */
		List<SubDeviceTypeStatusField> subDeviceTypeStatusFields = subDeviceTypeStatusFieldService.findBySubDeviceType(subDeviceType);
		model.addAttribute("subDeviceTypeStatusFields", subDeviceTypeStatusFields);
		
		return "backend/device_type_fields_result";
	}
	
	@RequestMapping(value = "/types/fields/{subDeviceTypeId}", method = RequestMethod.POST)
	public String addSubDeviceField(
			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
			@ModelAttribute SubDeviceTypeStatusField subDeviceTypeStatusField,
			Model model) {
		
		
		subDeviceTypeStatusFieldService.save(subDeviceTypeStatusField);
		
		return "redirect:/config/device/types/fields/" + subDeviceTypeId;
	}
	
	@RequestMapping(value = "/types/config/{subDeviceTypeId}", method = RequestMethod.GET)
	public String editSubDeviceConfigForm(
			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
			Model model) {
		
		// get the sub device type
		SubDeviceType subDeviceType = subDeviceTypeService.findById(subDeviceTypeId);
		if(subDeviceType == null) {
			model.addAttribute("message", "The SubDeviceType could not be found");
			return "backend/error";
		}
		
		model.addAttribute("title", "Edit configuration of " + subDeviceType.getSubDeviceType());
		model.addAttribute("description", "Edit the device type configuration.");
		
		// now get the SubDeviceTypeConfig
		SubDeviceTypeConfig subDeviceTypeConfig = subDeviceTypeConfigService.findBySubDeviceType(subDeviceType);
		model.addAttribute("subDeviceTypeConfig", subDeviceTypeConfig);
		
		return "backend/device_type_config_edit";
	}
	
	@RequestMapping(value = "/types/config/{subDeviceTypeId}", method = RequestMethod.POST)
	public String updateSubDeviceConfiguration(
			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
			@ModelAttribute SubDeviceTypeConfig subDeviceTypeConfig,
			Model model) {
		
		subDeviceTypeConfigService.save(subDeviceTypeConfig);
		
		return "redirect:/config/device/types";
	}

	
}

package nl.kolkos.dashboard.controllers;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

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
				
		return "backend/device_type_fields_result";
	}
	
	@RequestMapping(value = "/types/fields/form/{subDeviceTypeId}", method = RequestMethod.GET)
	public String showSubDeviceFieldForm(
			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
			Model model) {
		
		// get the sub device type
		SubDeviceType subDeviceType = subDeviceTypeService.findById(subDeviceTypeId);
		
		// get the used fields
		List<SubDeviceTypeField> usedFields = subDeviceTypeFieldService.findUsedFields(subDeviceType);
		model.addAttribute("usedFields", usedFields);
		
		// get the unused fields
		List<SubDeviceTypeField> unusedFields = subDeviceTypeFieldService.findUnusedFields(subDeviceType);
		model.addAttribute("unusedFields", unusedFields);
				
		return "backend/device_type_fields_form";
	}
	
	@RequestMapping(value = "/types/fields/form/{subDeviceTypeId}", method = RequestMethod.POST)
	public String handleChosenFields(
			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
			@RequestParam(value = "selectedFields[]", required = true) Long[] fields,
			@RequestParam("action") String action,
			Model model) {
		
		// first handle the form data
		SubDeviceType subDeviceType = subDeviceTypeService.findById(subDeviceTypeId);
		if(subDeviceType == null) {
			model.addAttribute("message", "The SubDeviceType could not be found");
			return "backend/error";
		}
		
		String message = "";
		
		// if the action is add, add the fields
		if(action.equals("add")) {
			// loop through fields
			if(fields != null) {
				for(long fieldId : fields) {
					// find the field
					SubDeviceTypeField subDeviceTypeField = subDeviceTypeFieldService.findById(fieldId);
					subDeviceTypeField.setUseField(true);
					subDeviceTypeFieldService.save(subDeviceTypeField);
				}
				message = "Field(s) added";
			}
		} else if (action.equals("remove")) {
			if(fields != null) {
				for(long fieldId : fields) {
					// find the field
					SubDeviceTypeField subDeviceTypeField = subDeviceTypeFieldService.findById(fieldId);
					subDeviceTypeField.setUseField(false);
					subDeviceTypeFieldService.save(subDeviceTypeField);
				}
				message = "Field(s) removed";
			}
		}
		
		
		model.addAttribute("message", message);
		model.addAttribute("messageClass", "alert alert-primary");
		
		
		// finally reload the data
		// get the used fields
		List<SubDeviceTypeField> usedFields = subDeviceTypeFieldService.findUsedFields(subDeviceType);
		model.addAttribute("usedFields", usedFields);
		
		// get the unused fields
		List<SubDeviceTypeField> unusedFields = subDeviceTypeFieldService.findUnusedFields(subDeviceType);
		model.addAttribute("unusedFields", unusedFields);
		
		
		
		
		
		return "backend/device_type_fields_form";
	}
	
//	@RequestMapping(value = "/types/fields/form/{subDeviceTypeId}", method = RequestMethod.POST)
//	public String handleChosenFields(
//			@PathVariable("subDeviceTypeId") long subDeviceTypeId,
//			@RequestParam Map<String,String> allRequestParams,
//			Model model) {
//		
//		System.out.println("called");
//		
//		
//		System.out.println(allRequestParams);
//		
//		for(String param : allRequestParams.keySet()) {
//			System.out.println(param);
//		}
//		
//		
//		
//		return "backend/device_type_fields_form";
//	}
	
}

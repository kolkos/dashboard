package nl.kolkos.dashboard.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.SubDeviceType;
import nl.kolkos.dashboard.objects.SubDeviceTypeField;
import nl.kolkos.dashboard.repositories.SubDeviceTypeFieldRepository;

@Service
public class SubDeviceTypeFieldService {
	@Autowired
	private SubDeviceTypeFieldRepository subDeviceFieldRepository;
	
	public void save(SubDeviceTypeField subDeviceField) {
		// check if this SubDeviceField already exists
		if(subDeviceFieldRepository.findBySubDeviceTypeAndField(subDeviceField.getSubDeviceType(), subDeviceField.getField()) == null) {
			// only save when it doesn't exists
			System.out.println(String.format("%s: Saving field '%s' for sub device type '%s'", new Date(), subDeviceField.getField(), subDeviceField.getSubDeviceType()));
			subDeviceFieldRepository.save(subDeviceField);
		} else {
			if(subDeviceField.getId() != null) {
				// uodate the device
				subDeviceFieldRepository.save(subDeviceField);
			}
		}
	}
	
	public List<SubDeviceTypeField> findBySubDeviceType(SubDeviceType subDeviceType){
		return subDeviceFieldRepository.findBySubDeviceTypeOrderByUseFieldDescFieldAsc(subDeviceType);
	}
	
	public SubDeviceTypeField findById(long id) {
		return subDeviceFieldRepository.findById(id);
	}
	
	//findBySubDeviceTypeAndUseFieldTrueOrderByFieldAsc
	public List<SubDeviceTypeField> findUsedFields(SubDeviceType subDeviceType){
		return subDeviceFieldRepository.findBySubDeviceTypeAndUseFieldTrueOrderByFieldAsc(subDeviceType);
	}
}

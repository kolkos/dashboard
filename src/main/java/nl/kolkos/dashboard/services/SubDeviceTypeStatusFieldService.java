package nl.kolkos.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;
import nl.kolkos.dashboard.repositories.SubDeviceTypeStatusFieldRepository;

@Service
public class SubDeviceTypeStatusFieldService {
	@Autowired
	private SubDeviceTypeStatusFieldRepository subDeviceTypeStatusFieldRepository;
	
	public List<SubDeviceTypeStatusField> findBySubDeviceType(SubDeviceType subDeviceType){
		return subDeviceTypeStatusFieldRepository.findBySubDeviceType(subDeviceType);
	}
	
	public void save(SubDeviceTypeStatusField subDeviceTypeStatusField) {
		subDeviceTypeStatusFieldRepository.save(subDeviceTypeStatusField);
	}
}

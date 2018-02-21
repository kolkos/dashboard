package nl.kolkos.dashboard.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.SubDeviceType;
import nl.kolkos.dashboard.repositories.SubDeviceTypeRepository;


@Service
public class SubDeviceTypeService {
	@Autowired
	private SubDeviceTypeRepository subDeviceTypeRepository;
	
	public SubDeviceType findBySubDeviceType(String subDeviceType) {
		return subDeviceTypeRepository.findBySubDeviceType(subDeviceType);
	}
	
	public void save(SubDeviceType subDeviceType) {
		subDeviceTypeRepository.save(subDeviceType);
	}
	
	
}

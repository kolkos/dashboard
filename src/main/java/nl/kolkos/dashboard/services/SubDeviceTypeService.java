package nl.kolkos.dashboard.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.SubDeviceType;
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
	
	public Iterable<SubDeviceType> findAll(){
		return subDeviceTypeRepository.findAll();
	}
	
	public SubDeviceType findById(Long id) {
		return subDeviceTypeRepository.findById(id);
	}
	
}

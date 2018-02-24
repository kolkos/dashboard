package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeConfig;
import nl.kolkos.dashboard.repositories.SubDeviceTypeConfigRepository;

@Service
public class SubDeviceTypeConfigService {
	@Autowired
	private SubDeviceTypeConfigRepository subDeviceTypeConfigRepository;
	
	public SubDeviceTypeConfig findBySubDeviceType(SubDeviceType subDeviceType) {
		return subDeviceTypeConfigRepository.findBySubDeviceType(subDeviceType);
	}
	
	public void save(SubDeviceTypeConfig subDeviceTypeConfig) {
		subDeviceTypeConfigRepository.save(subDeviceTypeConfig);
	}
}

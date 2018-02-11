package nl.kolkos.dashboard.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Device;
import nl.kolkos.dashboard.objects.SubDeviceType;
import nl.kolkos.dashboard.repositories.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	private DeviceRepository deviceRepository;
	
	public void save(Device device) {
		// check if the device already exists
		if(deviceRepository.findBySubDeviceTypeAndIdx(device.getSubDeviceType(), device.getIdx()) == null) {
			// only save when it does not exist
			System.out.println(String.format("%s: saving '%s' with idx '%d'", new Date(), device.getSubDeviceType().getSubDeviceType(), device.getIdx() ));
			deviceRepository.save(device);
		}
	}
	
	public Device findBySubDeviceTypeAndIdx(SubDeviceType subDeviceType, int ixd) {
		return deviceRepository.findBySubDeviceTypeAndIdx(subDeviceType, ixd);
	}
}

package nl.kolkos.dashboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.ContentDevice;
import nl.kolkos.dashboard.repositories.ContentDeviceRepository;

@Service
public class ContentDeviceService {
	@Autowired
	private ContentDeviceRepository contentDeviceRepository;
	
	public void save(ContentDevice contentDevice) {
		contentDeviceRepository.save(contentDevice);
	}
}

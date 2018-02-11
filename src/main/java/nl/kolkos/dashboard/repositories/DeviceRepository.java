package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.Device;
import nl.kolkos.dashboard.objects.SubDeviceType;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
	Device findBySubDeviceTypeAndIdx(SubDeviceType subDeviceType, int ixd);
	
	
}

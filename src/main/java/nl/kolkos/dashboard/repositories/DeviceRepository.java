package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.SubDeviceType;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
	Device findBySubDeviceTypeAndIdx(SubDeviceType subDeviceType, int ixd);
	
	List<Device> findAllByOrderByNameAsc();
}

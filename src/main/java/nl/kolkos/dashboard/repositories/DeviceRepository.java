package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DeviceTypeConfig;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
	List<Device> findAllByOrderByNameAsc();
	
	
	Device findByIdxAndDeviceTypeConfig(int idx, DeviceTypeConfig deviceTypeConfig);
	
	
	Device findById(Long id);
	
	
}

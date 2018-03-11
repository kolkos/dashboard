package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Device;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
	
	
	List<Device> findAllByOrderByNameAsc();
}

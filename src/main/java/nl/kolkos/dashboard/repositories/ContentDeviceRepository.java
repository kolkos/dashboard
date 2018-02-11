package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.ContentDevice;

@Repository
public interface ContentDeviceRepository extends CrudRepository<ContentDevice, Long> {
	
}

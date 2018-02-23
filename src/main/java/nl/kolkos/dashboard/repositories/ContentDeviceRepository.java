package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.ContentDevice;
import nl.kolkos.dashboard.entities.Panel;

@Repository
public interface ContentDeviceRepository extends CrudRepository<ContentDevice, Long> {
	ContentDevice findByPanel(Panel panel);
}

package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Device;
import nl.kolkos.dashboard.entities.DevicePanel;
import nl.kolkos.dashboard.entities.Panel;

@Repository
public interface DevicePanelRepository extends CrudRepository<DevicePanel, Long> {
	DevicePanel findByPanel(Panel panel);
	
	List<DevicePanel> findByDevice(Device device);
	
}

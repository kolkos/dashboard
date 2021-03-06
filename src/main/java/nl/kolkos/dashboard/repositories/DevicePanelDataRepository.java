package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.DevicePanelData;

@Repository
public interface DevicePanelDataRepository extends CrudRepository<DevicePanelData, Long>{

}

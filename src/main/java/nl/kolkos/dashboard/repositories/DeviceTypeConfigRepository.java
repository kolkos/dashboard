package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.DeviceTypeConfig;

@Repository
public interface DeviceTypeConfigRepository extends CrudRepository<DeviceTypeConfig, Long>{

}

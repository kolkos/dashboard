package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.DeviceType;

@Repository
public interface DeviceTypeRepository extends CrudRepository<DeviceType, Long> {

}

package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.DeviceTypeLookup;

@Repository
public interface DeviceTypeLookupRepository extends CrudRepository<DeviceTypeLookup, Long>{

	DeviceTypeLookup findByTypeAndSubTypeFieldAndSubTypeValue(String type, String subTypeField, String subTypeValue);
	
	
	DeviceTypeLookup findFirstByType(String type);
	
	
}

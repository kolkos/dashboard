package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;

@Repository
public interface SubDeviceTypeStatusFieldRepository extends CrudRepository<SubDeviceTypeStatusField, Long>{
	List<SubDeviceTypeStatusField> findBySubDeviceType(SubDeviceType subDeviceType);
}

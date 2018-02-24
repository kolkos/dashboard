package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.SubDeviceType;
import nl.kolkos.dashboard.entities.SubDeviceTypeConfig;

@Repository
public interface SubDeviceTypeConfigRepository extends CrudRepository<SubDeviceTypeConfig, Long>{
	SubDeviceTypeConfig findBySubDeviceType(SubDeviceType subDeviceType);
}

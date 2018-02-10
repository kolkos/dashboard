package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.SubDeviceType;

@Repository
public interface SubDeviceTypeRepository extends CrudRepository<SubDeviceType, Long> {
	SubDeviceType findBySubDeviceType(String subDeviceType);
}

package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.SubDeviceField;
import nl.kolkos.dashboard.objects.SubDeviceType;

@Repository
public interface SubDeviceFieldRepository extends CrudRepository<SubDeviceField, Long>{
	SubDeviceField findBySubDeviceTypeAndField(SubDeviceType subDeviceType, String field);
}

package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.SubDeviceTypeField;
import nl.kolkos.dashboard.objects.SubDeviceType;

@Repository
public interface SubDeviceTypeFieldRepository extends CrudRepository<SubDeviceTypeField, Long>{
	SubDeviceTypeField findBySubDeviceTypeAndField(SubDeviceType subDeviceType, String field);
	
	List<SubDeviceTypeField> findBySubDeviceTypeOrderByUseFieldDescFieldAsc(SubDeviceType subDeviceType);
	
	List<SubDeviceTypeField> findBySubDeviceTypeAndUseFieldTrueOrderByFieldAsc(SubDeviceType subDeviceType);
	
	SubDeviceTypeField findById(Long id);
}

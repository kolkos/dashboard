package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.PanelStatusField;
import nl.kolkos.dashboard.entities.SubDeviceTypeStatusField;

@Repository
public interface PanelStatusFieldRepository extends CrudRepository<PanelStatusField, Long> {
	// find all fields for this panel
	List<PanelStatusField> findByPanel(Panel panel);
	List<PanelStatusField> findByPanelOrderByPositionAsc(Panel panel);
	
	PanelStatusField findById(Long id);
	
	PanelStatusField findByPanelAndSubDeviceTypeStatusField(Panel panel, SubDeviceTypeStatusField subDeviceTypeStatusField);
	
	// this is used to get the last position
	PanelStatusField findFirstByPanelOrderByPositionDesc(Panel panel);
	
	
}

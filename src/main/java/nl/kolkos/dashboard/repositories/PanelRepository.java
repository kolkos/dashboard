package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.entities.Panel;
import nl.kolkos.dashboard.entities.Screen;

@Repository
public interface PanelRepository extends CrudRepository<Panel, Long>{
	Panel findByPanelIdAndScreen(String panelId, Screen screen);
	
	Panel findByPanelId(String panelId);
	
	Panel findById(Long id);
	
	List<Panel> findByScreen(Screen screen);
	
	List<Panel> findAllByOrderByNameAsc();
}

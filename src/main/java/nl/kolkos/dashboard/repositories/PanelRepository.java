package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.Panel;
import nl.kolkos.dashboard.objects.Row;

@Repository
public interface PanelRepository extends CrudRepository<Panel, Long>{
	public List<Panel> findByRowOrderByPosition(Row row);
}

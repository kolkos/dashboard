package nl.kolkos.dashboard.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.Row;
import nl.kolkos.dashboard.objects.Screen;

@Repository
public interface RowRepository extends CrudRepository<Row, Long>{
	List<Row> findByScreenOrderByPositionAsc(Screen screen);
	
}

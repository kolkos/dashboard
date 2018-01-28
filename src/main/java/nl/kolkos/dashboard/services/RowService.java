package nl.kolkos.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.objects.Row;
import nl.kolkos.dashboard.objects.Screen;
import nl.kolkos.dashboard.repositories.RowRepository;

@Service
public class RowService {
	@Autowired
	private RowRepository rowRepository;
	
	public void save(Row row) {
		rowRepository.save(row);
	}
	
	public List<Row> findRowsForScreen(Screen screen){
		return rowRepository.findByScreenOrderByPositionAsc(screen);
	}
	
}

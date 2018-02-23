package nl.kolkos.dashboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.kolkos.dashboard.entities.ContentType;
import nl.kolkos.dashboard.repositories.ContentTypeRepository;

@Service
public class ContentTypeService {
	@Autowired
	private ContentTypeRepository contentTypeRepository;
	
	public void save(ContentType contentType) {
		contentTypeRepository.save(contentType);
	}
	
	public void save(List<ContentType> contentTypes) {
		for(ContentType contentType : contentTypes) {
			this.save(contentType);
		}
	}
	
	public Iterable<ContentType> findAll(){
		return contentTypeRepository.findAll();
	}
	
	public ContentType findByName(String name) {
		return contentTypeRepository.findByName(name);
	}
}

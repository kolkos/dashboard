package nl.kolkos.dashboard.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;

@Service
public class BackendService {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	Resource[] loadResources(String pattern) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
    }
	
	
	
	public List<String> loadBackgroundImages() {
		List<String> images = new ArrayList<>();
		
		
		try {
			Resource[] resources = this.loadResources("classpath*:static/images/backgrounds/*.*");
			
			
			for(Resource resource : resources) {
				images.add(resource.getFilename());
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return images;
	}
}

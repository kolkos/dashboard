package nl.kolkos.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.kolkos.dashboard.objects.ContentType;

@Repository
public interface ContentTypeRepository extends CrudRepository<ContentType, Long>{

}

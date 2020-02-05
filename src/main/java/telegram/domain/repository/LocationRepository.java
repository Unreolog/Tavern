package telegram.domain.repository;

import org.springframework.data.repository.CrudRepository;
import telegram.domain.Location;

import java.util.List;


public interface LocationRepository extends CrudRepository<Location, Long> {

	Location findFirstById(Long id);
	List<Location> findAll();
}

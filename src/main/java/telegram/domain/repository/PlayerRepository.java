package telegram.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import telegram.domain.Player;

import java.util.List;


public interface PlayerRepository extends CrudRepository<Player, Long> {
	Player findFirstById(Long id);
	List<Player> findAll();
	List<Player> findAllByLocationId(Long id);
}

package telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.domain.Player;
import telegram.domain.repository.PlayerRepository;

@Service
public class RegistrationService {
    private PlayerRepository playerRepository;

    @Autowired
    public RegistrationService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player register(Message message) {
        Player player = new Player(
                Long.valueOf(message.getFrom().getId()),
                "Новичок",
                message.getFrom().getFirstName(),
                message.getFrom().getLastName(),
                message.getFrom().getUserName(),
                1002L,
                "newbie",
                "🍻 Трактир"
        );

        return playerRepository.save(player);
    }
}

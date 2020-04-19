package telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.domain.Player;
import telegram.domain.repository.PlayerRepository;
import telegram.handlers.BotHandler;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    private RegistrationService registrationService;
    private TravelService travelService;
    private BotHandler botHandler;

    private final Map<Long, Player> players = new TreeMap<>();

    @Autowired
    public PlayerService(PlayerRepository playerRepository,
                         RegistrationService registrationService,
                         TravelService travelService,
                         BotHandler botHandler) {
        this.playerRepository = playerRepository;
        this.registrationService = registrationService;
        this.travelService = travelService;
        this.botHandler = botHandler;
    }

    public synchronized void initUser(Message message) {
        Optional<Player> optional = getPlayer(message);
        Player player;
        if (optional.isPresent()) {
            player = optional.get();
            botHandler.sendMsg(message.getChatId(), "Hello! " + player.getUserName());
            botHandler.sendMsg(message.getChatId(), "С возвращением!" + player.getUserName());
            travelService.drawTravel(message, "С возвращением!", player);
        } else {
            player = registrationService.register(message);
            botHandler.sendMsg(message.getChatId(), "Поздравляем, персонаж создан!");
            travelService.drawTravel(message, "Приключения ждут!", player);
        }

        players.put(player.getId(), player);
    }

    public Optional<Player> getPlayer(Message message) {
        Long userId = Long.valueOf(message.getFrom().getId());
        return Optional.ofNullable(playerRepository.findFirstById(userId));
    }

    public void delete(Message message) {
        Optional<Player> optional = getPlayer(message);
        if (optional.isPresent()) {
            playerRepository.delete(optional.get());
            botHandler.sendMsg(message.getChatId(), "Ваш персонаж удалён");
        } else {
            botHandler.sendMsg(message.getChatId(), "У вас ещё нет персонажа");
        }
    }
}

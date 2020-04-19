package telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.Commands;
import telegram.GoBot;
import telegram.domain.Location;
import telegram.domain.Player;
import telegram.services.TravelService;
import telegram.services.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TravelHandler implements Handler {
    private TravelService travelService;
    private PlayerService playerService;
    private BotHandler botHandler;

    @Autowired
    public TravelHandler(TravelService travelService, PlayerService playerService, BotHandler botHandler) {
        this.travelService = travelService;
        this.playerService = playerService;
        this.botHandler = botHandler;
    }

    @Override
    public void process(Message message) {
		Optional<Player> optional = playerService.getPlayer(message);
		if (optional.isEmpty()) {
            botHandler.sendMsg(message.getChatId(), "Создайте своего персонажа /start");
            return;
		}


		travelService.move(message, optional.get());
    }

    @Override
    public String getCommand() {
        return null;
    }
}



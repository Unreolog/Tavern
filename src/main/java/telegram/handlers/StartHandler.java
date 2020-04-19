package telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.services.PlayerService;


@Component
public class StartHandler implements Handler {
    public static final String START = "/start";
    private PlayerService playerService;

    @Autowired
    public StartHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void process(Message message) {
        playerService.initUser(message);

    }

    @Override
    public String getCommand() {
        return START;
    }
}
package telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.services.PlayerService;


@Component
public class DeleteHandler implements Handler {
    public static final String DELETE = "/delete";
    private PlayerService playerService;

    @Autowired
    public DeleteHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void process(Message message) {
        playerService.delete(message);
    }

    @Override
    public String getCommand() {
        return DELETE;
    }
}
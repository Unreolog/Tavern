package telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.handlers.DeleteHandler;
import telegram.handlers.Handler;
import telegram.handlers.StartHandler;
import telegram.handlers.TravelHandler;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandController {
    private Map<String, Handler> handlers = new HashMap<>();

    private StartHandler startHandler;
    private DeleteHandler deleteHandler;
    private TravelHandler travelHandler;

    @Autowired
    public CommandController(StartHandler startHandler,
                             DeleteHandler deleteHandler,
                             TravelHandler travelHandler) {
        this.startHandler = startHandler;
        this.travelHandler = travelHandler;
        this.deleteHandler = deleteHandler;

        handlers.put(startHandler.getCommand(), startHandler);
        handlers.put(travelHandler.getCommand(), travelHandler);
        handlers.put(deleteHandler.getCommand(), deleteHandler);
    }


    public boolean runCommand(Message message){
        Handler handler = handlers.get(message.getText());
        if (handler == null) {
            return false;
        }
        handler.process(message);
        return true;
    }
}

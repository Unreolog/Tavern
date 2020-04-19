package telegram.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.Commands;
import telegram.GoBot;
import telegram.domain.Location;
import telegram.domain.Player;
import telegram.handlers.BotHandler;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelService {
    private BotHandler botHandler;

    @Autowired
    public TravelService(BotHandler botHandler) {
        this.botHandler = botHandler;
    }

    public void move(Message message, Player player) {
        String requestLocation = message.getText();
        Location playerLocation = locationsMap.get(player.getLocationName());

        if (locationsMap.containsKey(requestLocation) &&
                !(requestLocation.equals(Commands.WALL.getCommand())) &&
                !(requestLocation.equals(Commands.SEA.getCommand()))) {

            if (playerLocation.getName().equals(requestLocation)) {
                System.out.println("You already here");
                return;
            }

            if (playerLocation.getNeighbours().getNorthNeighbor().getName().equals(requestLocation) ||
                    playerLocation.getNeighbours().getSouthNeighbor().getName().equals(requestLocation) ||
                    playerLocation.getNeighbours().getWestNeighbor().getName().equals(requestLocation) ||
                    playerLocation.getNeighbours().getEastNeighbor().getName().equals(requestLocation)) {

                player.setLocationId(locationsMap.get(requestLocation).getId());
                player.setLocationName(locationsMap.get(requestLocation).getName());
                playerRepository.save(player);

                List<Player> playersNear = playerRepository.findAllByLocationId(player.getLocationId());
                String text = "Теперь ты здесь: " + player.getLocationName() + "\n\n" + "Игроки на локации:\n";
                for (Player neighbor : playersNear) {
                    text += neighbor.getUserName() + "\n";
                }
                drawTravel(message, text, player);
                return;
            }
        }

        drawTravel(message, "Туда не пройти", player);
    }

    public void drawTravel(Message message, String text, Player player) {
        Location playerLocation = locationsMap.get(player.getLocationName());

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();


        row.add(playerLocation.getNeighbours().getNorthNeighbor().getNeighbours().getWestNeighbor().getName());
        row.add(playerLocation.getNeighbours().getNorthNeighbor().getName());
        row.add(playerLocation.getNeighbours().getEastNeighbor().getNeighbours().getNorthNeighbor().getName());
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(playerLocation.getNeighbours().getWestNeighbor().getName());
        row.add(playerLocation.getName());
        row.add(playerLocation.getNeighbours().getEastNeighbor().getName());
        keyboard.add(row);

        row = new KeyboardRow();
        row.add(playerLocation.getNeighbours().getSouthNeighbor().getNeighbours().getWestNeighbor().getName());
        row.add(playerLocation.getNeighbours().getSouthNeighbor().getName());
        row.add(playerLocation.getNeighbours().getEastNeighbor().getNeighbours().getSouthNeighbor().getName());
        keyboard.add(row);

        GoBot.getBot().sendMsg(message, text, keyboard);
    }

}

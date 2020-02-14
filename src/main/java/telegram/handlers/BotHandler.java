package telegram.handlers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.Application;
import telegram.GoBot;
import telegram.domain.Location;
import telegram.domain.Player;
import telegram.domain.repository.LocationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BotHandler extends TelegramLongPollingBot {

	LocationRepository locationRepository = GoBot.context.getBean(LocationRepository.class);
	List<Location> locationList = locationRepository.findAll();
	private String botName = Application.getBotName();
	private String botToken = Application.getBotToken();
	private Player player = null;
	private boolean isReady = false;
	private HashMap<String, Location> locationsMap = new HashMap<>();


	// loading locations with relationships
	private void init() {
		for (Location location : locationList) {
			locationsMap.put(location.getName(), location);
		}

		player = new Player(1, "Mike", locationsMap.get("\uD83C\uDF7B Трактир"));   // a player for testing
		isReady = true;
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message.hasText()) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setChatId(message.getChatId());
			try {

				if (!isReady) { init(); }           // loading locations, create test player
				goTravel(message, player);            // move player

			} catch (Exception e) {
				sendMessage.setText("Что-то не так");
			}
		}
	}

	@Override
	public String getBotUsername() {
		return botName;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	public void goTravel(Message message, Player mike) {
		String result = mike.movePlayer(locationsMap, message.getText(), mike);
		sendMsg(message, result);
	}

	// to draw control buttons
	private void sendMsg(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setReplyToMessageId(message.getMessageId());
		sendMessage.setText(text);

		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();

		row.add(player.getLocation().getNeighbours().getNorthNeighbor().getNeighbours().getWestNeighbor().getName());
		row.add(player.getLocation().getNeighbours().getNorthNeighbor().getName());
		row.add(player.getLocation().getNeighbours().getEastNeighbor().getNeighbours().getNorthNeighbor().getName());
		keyboard.add(row);

		row = new KeyboardRow();
		row.add(player.getLocation().getNeighbours().getWestNeighbor().getName());
		row.add(player.getLocation().getName());
		row.add(player.getLocation().getNeighbours().getEastNeighbor().getName());
		keyboard.add(row);

		row = new KeyboardRow();
		row.add(player.getLocation().getNeighbours().getSouthNeighbor().getNeighbours().getWestNeighbor().getName());
		row.add(player.getLocation().getNeighbours().getSouthNeighbor().getName());
		row.add(player.getLocation().getNeighbours().getEastNeighbor().getNeighbours().getSouthNeighbor().getName());
		keyboard.add(row);

		keyboardMarkup.setKeyboard(keyboard);
		sendMessage.setReplyMarkup(keyboardMarkup);

		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
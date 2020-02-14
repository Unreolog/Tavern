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
	private Player mike = null;
	private boolean isReady = false;
	private HashMap<String, RealLocation> locationsMap = new HashMap<>();


	// loading locations with relationships
	private void init() {
		for (Location location : locationList) {
			locationsMap.put(location.getName(), new RealLocation(
					location.getId(),
					location.getName(),
					location.getCommand(),
					locationRepository.findFirstById(location.getNorthNeighbor()),
					locationRepository.findFirstById(location.getSouthNeighbor()),
					locationRepository.findFirstById(location.getWestNeighbor()),
					locationRepository.findFirstById(location.getEastNeighbor())
			));
		}

		mike = new Player(1, "Mike", locationsMap.get("\uD83C\uDF7B Трактир"));   // a player for testing
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
				goTravel(message, mike);            // move player

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
		row.add(locationsMap.get(mike.getLocation().getNorthNeighbor().getName()).getWestNeighbor().getName());
		row.add(mike.getLocation().getNorthNeighbor().getName());
		row.add(locationsMap.get(mike.getLocation().getEastNeighbor().getName()).getNorthNeighbor().getName());
		keyboard.add(row);
		row = new KeyboardRow();
		row.add(mike.getLocation().getWestNeighbor().getName());
		row.add(mike.getLocation().getName());
		row.add(mike.getLocation().getEastNeighbor().getName());
		keyboard.add(row);
		row = new KeyboardRow();
		row.add(locationsMap.get(mike.getLocation().getSouthNeighbor().getName()).getWestNeighbor().getName());
		row.add(mike.getLocation().getSouthNeighbor().getName());
		row.add(locationsMap.get(mike.getLocation().getEastNeighbor().getName()).getSouthNeighbor().getName());
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
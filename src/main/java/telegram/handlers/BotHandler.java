package telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.Commands;
import telegram.configure.ServerConfiguration;
import telegram.services.CommandController;

import javax.annotation.Nullable;
import java.util.List;


@Component
public class BotHandler extends TelegramLongPollingBot {

	private ServerConfiguration serverConfiguration;
	private CommandController commandController;

	@Autowired
	public BotHandler(ServerConfiguration serverConfiguration) {
		this.serverConfiguration = serverConfiguration;
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message.hasText()) {
			boolean isOk = commandController.runCommand(message);
			if (!isOk) sendMsg(message.getChatId(), "Создай персонажа командой /start");
		}
	}

	public void sendMsg(Long chatId, String text) {
		sendMsg(chatId, text, null);
	}


	public void sendMsg(Long chatId, String text, @Nullable List<KeyboardRow> keyboard) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId.toString());
		sendMessage.setText(text);
		ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
		sendMessage.setReplyMarkup(replyKeyboardRemove);
		if (keyboard != null) {
			ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
			keyboardMarkup.setKeyboard(keyboard);
			keyboardMarkup.setResizeKeyboard(true);
			sendMessage.setReplyMarkup(keyboardMarkup);
		}

		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return serverConfiguration.getBotName();
	}

	@Override
	public String getBotToken() {
		return serverConfiguration.getBotToken();
	}
}
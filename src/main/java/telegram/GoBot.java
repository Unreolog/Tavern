package telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.handlers.BotHandler;


@Component
public class GoBot {
	public BotHandler bot;

	@Autowired
	public GoBot(BotHandler bot) {
		this.bot = bot;
	}

	public void run() {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}

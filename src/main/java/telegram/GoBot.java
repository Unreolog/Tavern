package telegram;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.handlers.BotHandler;


public class GoBot {
	public static ApplicationContext context;
	public static BotHandler bot;

	public static void main(ConfigurableApplicationContext context, String[] args) {
		GoBot.context = context;

		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		bot = new BotHandler();

		try {
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public static BotHandler getBot() {
		return bot;
	}
}

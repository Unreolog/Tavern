package telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@ConfigurationProperties(prefix = "bot")
public class BotApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(BotApplication.class);


	@Autowired
	private GoBot goBot;

	public static String botName;
	public static String botToken;
	private static List<String> botSettings = new ArrayList<String>();

	public static void main(String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}

	public static String getBotName() {
		return botName;
	}

	public static String getBotToken() {
		return botToken;
	}

	public List<String> getSettings() {
		return this.botSettings;
	}

	@Override
	public void run(String... args) throws Exception {
		goBot.run();
	}
}
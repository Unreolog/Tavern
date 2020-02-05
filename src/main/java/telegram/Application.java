package telegram;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@ConfigurationProperties(prefix = "bot")
public class Application implements CommandLineRunner {

	public static String botName;
	public static String botToken;
	private static List<String> botSettings = new ArrayList<String>();

	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
		botName = botSettings.get(0).split(" ")[0];
		botToken = botSettings.get(0).split(" ")[1];

		GoBot.main(app, args);
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

	}
}
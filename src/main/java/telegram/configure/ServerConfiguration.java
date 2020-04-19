package telegram.configure;


import org.springframework.context.annotation.Configuration;
import telegram.infrastructure.JacksonConfiguration;
import telegram.infrastructure.JacksonConfigurationSources;

@Configuration
@JacksonConfigurationSources({
        "classpath*:config/server.yml"
})
public class ServerConfiguration extends JacksonConfiguration {
    private String botName;
    private String botToken;

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}

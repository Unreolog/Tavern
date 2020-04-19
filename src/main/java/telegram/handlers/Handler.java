package telegram.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;


public interface Handler {
	void process(Message message);

    String getCommand();
}

package telegram;

import telegram.handlers.Handler;
import telegram.handlers.StartHandler;
import telegram.handlers.TravelHandler;

import java.util.HashMap;

//ToDo remove this enum
@Deprecated
public enum Commands {
	/*START("/start", new StartHandler()),
	CREATE("/create", new StartHandler()),
	DELETE("/delete", new StartHandler()),

	PORT_PUB("🍻 Трактир", new TravelHandler()),
	PORT_LIGHTHOUSE("🗼 Маяк", new TravelHandler()),
	PORT_DOCK_3("⛯ Причал №3", new TravelHandler()),
	SEA("🌊 Море", new TravelHandler()),
	PORT_BARRACKS("🛏️ Бараки", new TravelHandler()),
    PORT_DOCK_1("⛯ Причал №1", new TravelHandler()),
	PORT_WAREHOUSE("📦 Склад", new TravelHandler()),
	PORT_GUARD("⚔️Пост стражи", new TravelHandler()),
    PORT_DOCK_2("⛯ Причал №2", new TravelHandler()),
	PORT_MARKET("🐠 Рыбацкий рынок", new TravelHandler()),
	DUGARD_EAST("🏰 Восточный Дуград", new TravelHandler()),
	WALL("🧱 Стена", new TravelHandler()),*/
	;

	private static final HashMap<String, Commands> map = new HashMap<>();
	private final String command;
	private final Handler handler;

	public String getCommand() {
		return command;
	}

	Commands(String command, Handler handler) {
		this.command = command;
		this.handler = handler;
	}

	public static Commands getCommand(String text) {
		if (map.isEmpty()) {
			for (Commands commands : values()) {
				map.put(commands.command, commands);
			}
		}
		return map.get(text);
	}

	public Handler getHandler() {
		return handler;
	}
}

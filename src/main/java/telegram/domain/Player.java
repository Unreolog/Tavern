package telegram.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


@Data
@Setter
@Getter
public class Player {
	private int id;
	private String name;
	private RealLocation location;

	public Player(int id, String name, RealLocation realLocation) {
		this.id = id;
		this.name = name;
		this.location = realLocation;
	}

	@Override
	public String toString() {
		return "Player{" +
				"id=" + id +
				", name='" + name + '\'' +
				", location=" + location +
				'}';
	}


	public String movePlayer(HashMap<String, RealLocation> locationsMap, String command, Player mike) {
		if (locationsMap.containsKey(command) &&
				!(command.equals("\uD83C\uDF0A Море")) &&
				!(command.equals("\uD83D\uDC83 Бордель")) &&
				!(command.equals("\uD83E\uDDF1 Стена"))) {

			if (locationsMap.get(command).getCommand() == this.getLocation().getCommand()) {
				System.out.println("You already here");
				return "Ты уже в этой локации";
			}

			if (this.getLocation().getNorthNeighbor().getName().equals(command) ||
					this.getLocation().getSouthNeighbor().getName().equals(command) ||
					this.getLocation().getWestNeighbor().getName().equals(command) ||
					this.getLocation().getEastNeighbor().getName().equals(command)) {
				this.setLocation(locationsMap.get(command));
				System.out.println("\nКрасава, умеешь читать!\n");
				return "Теперь ты здесь: " + mike.getLocation().getName();
			}
		}
		System.out.println("You shall not pass!");
		return "Туда не пройти";
	}
}
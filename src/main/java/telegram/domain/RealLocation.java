package telegram.domain;

import lombok.Data;


@Data
public class RealLocation {
	private Long id_location;
	private String name;
	private String command;
	private Location northNeighbor;
	private Location southNeighbor;
	private Location westNeighbor;
	private Location eastNeighbor;

	public RealLocation(Long id_location, String name, String command, Location northNeighbor, Location southNeighbor, Location westNeighbor, Location eastNeighbor) {
		this.id_location = id_location;
		this.name = name;
		this.command = command;
		this.northNeighbor = northNeighbor;
		this.southNeighbor = southNeighbor;
		this.westNeighbor = westNeighbor;
		this.eastNeighbor = eastNeighbor;
	}

}

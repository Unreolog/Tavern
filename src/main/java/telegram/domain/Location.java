package telegram.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "Locations")
public class Location {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "command")
	private String command;
	@Column(name = "north_neighbor")
	private Long northNeighbor;
	@Column(name = "south_neighbor")
	private Long southNeighbor;
	@Column(name = "west_neighbor")
	private Long westNeighbor;
	@Column(name = "east_neighbor")
	private Long eastNeighbor;

	@Override
	public String toString() {
		return "Location{" +
				"id=" + id +
				", name='" + name + '\'' +
				", command='" + command + '\'' +
				", northNeighbor=" + northNeighbor +
				", southNeighbor=" + southNeighbor +
				", westNeighbor=" + westNeighbor +
				", eastNeighbor=" + eastNeighbor +
				'}';
	}
}
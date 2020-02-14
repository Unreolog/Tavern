package telegram.domain;

import lombok.Data;

import javax.persistence.*;


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

	@Embedded
	private LocationNeighbours neighbours;

	@Override
	public String toString() {
		return "Location{" +
				"id=" + id +
				", name='" + name + '\'' +
				", command='" + command + '\'' +
				", northNeighbor=" + neighbours.getNorthNeighbor() +
				", southNeighbor=" + neighbours.getSouthNeighbor() +
				", westNeighbor=" + neighbours.getWestNeighbor() +
				", eastNeighbor=" + neighbours.getEastNeighbor() +
				'}';
	}
}
package telegram.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
@Data
public class LocationNeighbours {
	//	@ManyToOne(fetch = FetchType.LAZY)
	//	@JoinColumn(name = "in_up")
	//	private Location up;
	//
	//	@ManyToOne(fetch = FetchType.LAZY)
	//	@JoinColumn(name = "in_down")
	//	private Location down;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "west_neighbor")
	private Location westNeighbor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "east_neighbor")
	private Location eastNeighbor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "north_neighbor")
	private Location northNeighbor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "south_neighbor")
	private Location southNeighbor;
}
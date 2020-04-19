package telegram.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Player {
	@Id
	@Column(name = "id_telegram")
	private Long id;
	@Column(name = "character_name")
	private String characterName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "username")
	private String userName;
	@Column(name = "location_id")
	private Long locationId;
	@Column(name = "state")
	private String state;

	@Column(name = "location_name")
	private String locationName;

	public Player() {
	}

	public Player(Long id, String characterName, String firstName, String lastName, String userName, Long locationId, String state, String locationName) {
		this.id = id;
		this.characterName = characterName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.locationId = locationId;
		this.state = state;
		this.locationName = locationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
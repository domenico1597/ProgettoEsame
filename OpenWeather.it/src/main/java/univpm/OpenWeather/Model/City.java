package univpm.OpenWeather.Model;

/**
 * Classe City contenente le informazioni generali della città e un oggetto di
 * Position per leggere e settare le coordinate
 * 
 *
 */
public class City extends Position {

	private long id;
	private String name;
	private Position coordinates;

	public City(long id, String name, Position coordinates) {
		super();
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
	}

	public City(long id, String name) {
		// TODO Auto-generated constructor stub
	}

	public City(String name) {
		this.name = name;
	}

	public City() {
		// TODO Auto-generated constructor stub
		// super();
	}

	// Getters and setters

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCityName() {
		return name;
	}

	public void setCityName(String name) {
		this.name = name;
	}

	public Position getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Position coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", coordinates=" + coordinates + "]";
	}

}

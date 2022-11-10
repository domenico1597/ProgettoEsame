package univpm.OpenWeather.Model;

/**
 * Classe Position contenente le informazioni sulle coordinate di una città.
 * 
 *
 */
public class Position {

	private double latitude;
	private double longitude;

	public Position(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Position() {

	}

	// getters and setters
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Position [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}

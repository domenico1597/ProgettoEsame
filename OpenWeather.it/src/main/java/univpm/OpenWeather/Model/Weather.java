package univpm.OpenWeather.Model;

/**
 * Classe contenente le informazioni sul meteo e un oggetto di City per prendere
 * le informazioni sulla città e sulla posizione
 * 
 *
 */

public class Weather extends City {

	/**
	 * ho cambiato description (prima era feels like)
	 */
	private String description; // Descrizione del tempo (es. "Soleggiato")
	private Double temp;
	private double temp_min;
	private double temp_max;
	private long pressure;
	private long date;
	private String main;
	private City city;

	// Constructors
	public Weather(Double temp, String description, double temp_min, double temp_max, long pressure, long date,
			String main, City city) {
		this.temp = temp;
		this.description = description;
		this.temp_min = temp_min;
		this.main = main;
		this.temp_max = temp_max;
		this.pressure = pressure;
		this.date = date;
		this.city = city;
	}

	public Weather(Double temp, String description, double temp_min, double temp_max, long pressure, long date,
			String main) {
		this.temp = temp;
		this.description = description;
		this.temp_min = temp_min;
		this.main = main;
		this.temp_max = temp_max;
		this.pressure = pressure;
		this.date = date;

	}

	public Weather(long id, String name, Position coordinates) {
		// TODO Auto-generated constructor stub
		super(id, name, coordinates);
	}

	public Weather() {
		// TODO Auto-generated constructor stub
	}

	// Getters & Setters

	public double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}

	public double getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}

	public long getPressure() {
		return pressure;
	}

	public void setPressure(long pressure) {
		this.pressure = pressure;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Weather [feels_like=" + description + ", temp_min=" + temp_min + ", temp_max=" + temp_max
				+ ", pressure=" + pressure + ", date=" + date + "]";
	}

}

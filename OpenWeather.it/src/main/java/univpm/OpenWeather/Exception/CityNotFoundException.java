package univpm.OpenWeather.Exception;

/**
 * classe per eccezioni sull' inserimento errato del nome di una città
 * 
 */

@SuppressWarnings("serial")
public class CityNotFoundException extends Exception {

	public CityNotFoundException() {
		System.out.println("City not found, please enter a different city name");
	}

	public CityNotFoundException(String mes) {
		super(mes);
	}

}

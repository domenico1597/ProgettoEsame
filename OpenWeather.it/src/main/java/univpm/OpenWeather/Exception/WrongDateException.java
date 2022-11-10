package univpm.OpenWeather.Exception;

/**
 * Questa classe serve a gestire eccezioni quando si immette un formato di data
 * sbagliato
 * 
 *
 */
@SuppressWarnings("serial")
public class WrongDateException extends Exception {

	public WrongDateException() {
		// TODO Auto-generated constructor stub
	}

	public WrongDateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}

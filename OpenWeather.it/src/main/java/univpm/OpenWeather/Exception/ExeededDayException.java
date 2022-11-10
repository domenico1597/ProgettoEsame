package univpm.OpenWeather.Exception;

/**
 * Questa eccezione viene lanciata dalla classe FiltersImpl se il giorno
 * inserito non è valido perchè troppo vicino o troppo lontano dal momento
 * attuale. Questo perchè le previsioni sono fatte una volta ogni 3 ore e per un
 * massimo di 5 giorni.
 * 
 *
 */
@SuppressWarnings("serial")
public class ExeededDayException extends Exception {

	public ExeededDayException() {
		System.out.println("Il giorno inserito non è valido");
	}

	public ExeededDayException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}

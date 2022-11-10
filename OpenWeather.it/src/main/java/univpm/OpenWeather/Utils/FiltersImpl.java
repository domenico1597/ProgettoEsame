package univpm.OpenWeather.Utils;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import univpm.OpenWeather.Exception.CityNotFoundException;
import univpm.OpenWeather.Exception.ExeededDayException;
import univpm.OpenWeather.Exception.WrongDateException;
import univpm.OpenWeather.Service.WeatherImpl;

/**
 * questa classe implementa l'interfaccia FiltersInt e da la possibilità di
 * selezionare un periodo di tempo sul quale far visualizzare le previsioni, che
 * saranno stampate grazie a PrintInfo nel service.
 * 
 * @author lucas *
 */
public class FiltersImpl implements FiltersInt {
	private JSONObject forecast;
	private JSONArray days;

	WeatherImpl service = new WeatherImpl();
	Utils u = new Utils();

	/**
	 * Questo è il costruttore della classe che invoca il metodo @method getForecast
	 * del Service @WeatherImpl per generare un JSONObject contenente un JSONArray
	 * con le previsioni.
	 * 
	 * @param name nome della città
	 * @throws MalformedURLException
	 * @throws ParseException
	 * @throws CityNotFoundException
	 */
	public FiltersImpl(String name) throws MalformedURLException, ParseException, CityNotFoundException {
		this.setForecast(service.getForecast(name));
		this.days = (JSONArray) this.forecast.get("Forecasts");

	}

	/**
	 * Questo metodo crea una data sotto forma di testo in base al numero passatogli
	 * come parametro. Questo numero viene aggiunto al tempo corrente per ottenere
	 * una data futura di inizio o fine per le previsioni.
	 * 
	 * @throws ExeededDayException se la data selezionata è tra più di 5 giorni o
	 *                             tra meno di tre ore
	 */
	@Override
	public String setDate(long number) throws ParseException, ExeededDayException {
		long seconds;// 86400 secondi in un giorno
		String text = "";

		if (number > 432000) {// numero di secondi in 5 giorni
			throw new ExeededDayException("La previsione cercata è tra più di 5 giorni da ora");
		} else if (number == 0) {
			seconds = 0;// setta la data al momento presente
		} else if (number < 10800) {// numero di secondi in tre ore
			throw new ExeededDayException("La previsione cercata è tra meno di 3 ore da ora");
		} else {
			seconds = number;// setta la data ad un momento futuro in base a quanto è grande number
		}

		Date date = u.toDate((System.currentTimeMillis() / 1000) + seconds);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		text = format.format(date);
		return text;

	}

	/**
	 * Questo metodo restituisce un JSONArray con le previsioni meteo tra i due
	 * momenti richiesti start e finish. Sia start che finish devono avere un
	 * formato del tipo dd-MM-yyyy HH:mm per essere elaborati. Se l'orario (di start
	 * o finish) richiesto è compreso tra una previsione e l'altra (dato che vengono
	 * fatte ad intervalli di tre ore) la previsione andrà per eccesso nel caso di
	 * start e per difetto nel caso di finish.
	 * 
	 * @param start  data e orario di inizio
	 * @param finish data e orario di fine
	 * @throws ParseException
	 * @throws ExeededDayException se la data selezionata è tra più di 5 giorni o
	 *                             tra meno di tre ore
	 * @throws WrongDateException  se lo start è dopo il finish
	 * 
	 * @return printAll è un JSONObject con la descrizione dello start e finish
	 *         della previsione e un JSONArray di previsioni ad intervalli di tre
	 *         ore
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject FromStartToFinish(String start, String finish)
			throws ParseException, ExeededDayException, WrongDateException {

		Iterator<JSONObject> i = this.days.iterator();
		JSONArray toPrint = new JSONArray();

		Date datainizio = u.dateConverter(start);

		Date datafine = u.dateConverter(finish);
		if (datafine.compareTo(datainizio) < 0) {
			throw new WrongDateException("La data di inizio è successiva alla data di fine");
		}
		if (datafine.compareTo(u.dateConverter(setDate(432000))) > 0) {
			throw new WrongDateException("La data di fine è tra più di 5 giorni");
		}

		while (i.hasNext()) {
			JSONObject temp = i.next();
			Date tempDate = u.dateConverter((String) temp.get("date"));
			if (tempDate.compareTo(datainizio) > 0 && tempDate.compareTo(datafine) < 0) {
				toPrint.add(temp);
				tempDate = null;
			}

		}
		JSONObject printAll = new JSONObject();
		printAll.put("Previsioni da " + datainizio.toString().substring(0, 16) + " a "
				+ datafine.toString().substring(0, 16), toPrint);
		if (toPrint.isEmpty()) {
			throw new ExeededDayException("La previsione cercata è tra meno di 3 ore da ora");
		}
		return printAll;
	}

	/**
	 * A scopo didattico si possono lasciare i setter e getters per usare questo
	 * metodo in altre circostanze. Il metodo funzionerà affinchè il JSONArray
	 * passato sarà strutturato come quello creato nel costruttore
	 * 
	 */
	// setter e getter
	public JSONObject getForecast() {
		return forecast;
	}

	public void setForecast(JSONObject forecast) {
		this.forecast = forecast;
	}

}

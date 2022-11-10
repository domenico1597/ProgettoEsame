package univpm.OpenWeather.Controller;


import java.net.MalformedURLException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import univpm.OpenWeather.Exception.CityNotFoundException;
import univpm.OpenWeather.Exception.ExeededDayException;
import univpm.OpenWeather.Exception.WrongDateException;
import univpm.OpenWeather.Service.WeatherImpl;
import univpm.OpenWeather.Utils.FiltersImpl;

@RestController
public class OpenWeatherController {

	@Autowired
	WeatherImpl service;


	/**
	 * Questa rotta permette di stampare le condizioni meteorologiche della città
	 * inserita, se non è inserita nessuna città la scelta di default sarà Ancona.
	 * 
	 * @param name nome della città
	 * @return JSONObject rappresentazione delle condizioni meteorologiche della
	 *         città
	 * @throws MalformedURLException avvisa se la chiamata non è andata a buon fine
	 *
	 */
	@GetMapping("/current")
	public ResponseEntity<Object> current(@RequestParam(name = "name", defaultValue = "Ancona") String name)
			throws Exception {
		try {
			return new ResponseEntity<>(service.printInfo(service.getWeather(name), true), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Questa rotta permette di stampare le previsioni meteorologiche per i
	 * successivi 5 giorni dalla chiamata con intervalli di tre ore.
	 * 
	 * @param name della città
	 * @return JSONObject con le previsioni per i successivi 5 giorni ad intervalli
	 *         di 3 ore
	 * @throws Exception
	 * 
	 */
	@GetMapping("/forecast")
	public ResponseEntity<Object> forecast(@RequestParam(name = "name", defaultValue = "Ancona") String name)
			throws Exception, CityNotFoundException {

		try {
			return new ResponseEntity<>(service.getForecast(name), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	/**
	 * Questa rotta consente di salvare dati sulle statistiche sull'errore
	 *  di temperatura per una data città 
	 * @param name nome della città della quale si vuole calcolare le statistiche
	 *  sull'errore tra il forecast e l'attuale
	 * @return
	 * @throws MalformedURLException
	 * @throws ParseException
	 * @throws CityNotFoundException
	 */
	@GetMapping("/errors")
	public ResponseEntity<Object> errors(@RequestParam(name = "name", defaultValue = "Ancona") String name)
			throws MalformedURLException, ParseException, CityNotFoundException {

		try {
			return new ResponseEntity<>(service.getErrors(name), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/** Questa rotta consente di stampare le statistiche sull'errore
	 *  della città richiesta tramite la rotta /errors
     */
	@GetMapping("/errStat")
	public ResponseEntity<Object> errStat() {

		try {
			return new ResponseEntity<>( service.getStatistics(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	

	/**
	 * Questa rotta permette di stampare tutte le previsioni ad intervalli di tre
	 * ore dal momento Start a Finish. La data deve essere scritta in formato
	 * dd-MM-yyyy , mentre l'orario HH:mm .
	 * 
	 * @param name       nome della città
	 * @param start      data d'inizio
	 * @param finish     data di fine
	 * @param startTime  orario d'inizio
	 * @param finishTime orario di fine
	 * 
	 * @throws MalformedURLException
	 * @throws ParseException
	 * @throws ExeededDayException   se lo start richiesto è a meno di 3 ore dal
	 *                               momento attuale, o se il finish richiesto è a
	 *                               più fi 5 giorni dal momento attuale.
	 * @throws WrongDateException    se la data d'inizio è posteriore a quella di
	 *                               fine
	 * @throws CityNotFoundException
	 * 
	 * @return Un @JSONObject con le previsioni nell'intervallo richiesto
	 */
	@GetMapping("/filters")
	public ResponseEntity<Object> filters(@RequestParam(name = "name", defaultValue = "Milano") String name,
			@RequestParam(name = "start", defaultValue = "now") String start,
			@RequestParam(name = "finish", defaultValue = "five") String finish,
			@RequestParam(name = "startTime", defaultValue = "00:00") String startTime,
			@RequestParam(name = "finishTime", defaultValue = "00:00") String finishTime) throws MalformedURLException,
			ParseException, ExeededDayException, WrongDateException, CityNotFoundException {
		String format = "dd-MM-yyyy";
		if (!start.equals(format)) {

		}
		FiltersImpl f = new FiltersImpl(name);
		if (start.equals("now")) {
			start = f.setDate(0);
		}
		if (finish.equals("five")) {
			finish = f.setDate(432000);
		}

		try {
			return new ResponseEntity<>(f.FromStartToFinish(start + " " + startTime, finish + " " + finishTime),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}

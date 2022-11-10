package univpm.OpenWeather.Service;


import java.io.InputStreamReader;

import java.io.Reader;
import java.net.MalformedURLException;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.stereotype.Service;

import univpm.OpenWeather.Utils.StatisticCalculator;
import univpm.OpenWeather.Exception.CityNotFoundException;
import univpm.OpenWeather.Exception.StatisticException;
import univpm.OpenWeather.Model.Weather;
import univpm.OpenWeather.Utils.Utils;
import univpm.OpenWeather.Utils.GetFromCall;

@Service
public class WeatherImpl implements WeatherInt {

	private String apiKey = "b62da4607b5691ab675e804c056d55d0";
	private String url = "https://api.openweathermap.org/data/2.5/";

	/**
	 * Questo metodo setta la stringa url per effettuare la chiamata.
	 * 
	 * @param current se è true crea una stringa per chiamata current(meteo
	 *                attuale), se è false fa una chiamata forecast (previsione per
	 *                5 gg).
	 */
	@Override
	public String UrlBuilder(boolean current, String cityName) {

		if (current == true) {// current weather
			this.url += "weather?q=" + cityName + ",IT" + "&appid=" + this.apiKey;
		} else if (current == false) {// 5day forecast
			this.url += "forecast?q=" + cityName + ",IT" + "&appid=" + this.apiKey;
		}
		return this.url;
	}

	/**
	 * Questo metodo crea una connessione alle API e restituisce un JSONObject
	 * contenente tutte le informazioni di quell'API
	 * 
	 * @param api API grazie alla quale si connette e prende informazioni il metodo
	 * @throws CityNotFoundException
	 */
	@Override
	public JSONObject getInfo(String api) throws MalformedURLException, CityNotFoundException {

		JSONObject obj = null;
		URL url = new URL(api);
		try {

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			int responseCode = conn.getResponseCode();

			if (responseCode != 200) {
				throw new Exception("HttpResponseCode: " + responseCode);

			} else {
				Reader scan = new InputStreamReader(url.openStream());

				JSONParser parser = new JSONParser();

				obj = (JSONObject) parser.parse(scan);

				scan.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (obj == null) {
			throw new CityNotFoundException("City not found, please enter a different city name");
		}
		return obj;

	}

	/**
	 * @param cityName è il nome della città per cui si vuole richiedere la
	 *                 previsione.
	 * @method getInfoCity prende le informazioni riguardanti: coordinate,nome, id
	 * @method getDailyWeather prende le indformazioni riguardanti: temperature,
	 *         pressioni,descrizione meteo
	 * @throws CityNotFoundException
	 * @return Crea un oggetto di @Weather e valorizza
	 * 
	 */
	@Override
	public Weather getWeather(String cityName) throws MalformedURLException, CityNotFoundException {// , Weather meteo

		GetFromCall p = new GetFromCall();
		ResetUrl();
		String u = UrlBuilder(true, cityName); // Crea URL

		JSONObject object = getInfo(u); // JSONObject contentente il JSON
		Weather meteo = p.createWeather(object, true);

		return meteo;
	}

	/**
	 * @param cityName è il nome della città per cui si vuole richiedere la
	 *                 previsione.
	 * @return Questo metodo stampa un @JSONObject contenente un @JSONArray con le
	 *         previsioni per i cinque giorni successivi al momento della chiamata
	 *         ad intervalli di tre ore. Contiene anche un @JSONObject con le
	 *         informazioni della città.
	 * @throws CityNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getForecast(String cityName) throws MalformedURLException, ParseException, CityNotFoundException {
		GetFromCall p = new GetFromCall();

		ResetUrl();
		String u = UrlBuilder(false, cityName);

		JSONObject object = getInfo(u);// ottiene il JSONObject con tutte le previsioni

		JSONArray list = (JSONArray) object.get("list");// seleziono l'array contenente le informazioni del meteo

		Iterator<JSONObject> i = list.iterator();// creo un iteratore

		JSONObject toPrint = new JSONObject();
		JSONArray dayArray = new JSONArray();

		while (i.hasNext()) {

			JSONObject temp = i.next();
			Weather meteo = p.createWeather(temp, false);

			dayArray.add(printInfo(meteo, false));

		}

		toPrint.put("City", object.get("city"));
		toPrint.put("Forecasts", dayArray);
		return toPrint;
	}

	/**
	 * Questo metodo stampa le informazioni della città e del meteo se all è true
	 * mentre stampa solo quelle relative al meteo se all è false.
	 * 
	 * @param all
	 * @param meteo oggetto di Weather
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject printInfo(Weather meteo, boolean all) {
		Utils u = new Utils();
		JSONObject allInfo = new JSONObject();

		JSONObject cityInfo = new JSONObject();
		JSONObject weatherInfo = new JSONObject();

		if (all) {
			// info stampate se all é true

			cityInfo.put("Coordinates", meteo.getCity().getCoordinates());
			cityInfo.put("Name", meteo.getCity().getCityName());
			cityInfo.put("Id", meteo.getCity().getId());
		}

		// info stampate se all é false
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String strDate = dateFormat.format(u.toDate(meteo.getDate()));
		weatherInfo.put("date", strDate);
		JSONObject weather = new JSONObject();
		weather.put("Weather", meteo.getMain());
		weather.put("Specific", meteo.getDescription());
		weather.put("Pressure", meteo.getPressure() + " Pa");
		weatherInfo.put("Status", weather);

		JSONObject temp = new JSONObject();
		temp.put("Minimum", (u.tempConverter(meteo.getTemp_min()) + " °C"));
		temp.put("Current", (u.tempConverter(meteo.getTemp()) + " °C"));
		temp.put("Maximum", (u.tempConverter(meteo.getTemp_max()) + " °C"));
		weatherInfo.put("Temperatures", temp);

		allInfo.put("City", cityInfo);
		allInfo.put("Forecasts", weatherInfo);

		if (all) {
			return allInfo;
		} else {
			return weatherInfo;
		}

	}


	
	/**
	 * Metodo che salva le statistiche sull'errore tra il forecast e l'attuale (nel forecast
	 * prende solo il primo JSONObject)
	 * 
	 * @param name è il nome della città di cui si vuole calcolare le statistiche sull'errore
	 * @return 
	 * @return
	 * @throws MalformedURLException
	 * @throws ParseException
	 * @throws CityNotFoundException
	 */
	@SuppressWarnings("unchecked")
    public String getErrors(String name) throws MalformedURLException, StatisticException, ParseException, CityNotFoundException {
        
        StatisticCalculator statisticCalculator=new StatisticCalculator();
        Utils u = new Utils();
        JSONObject current = printInfo(getWeather(name), false);


       JSONObject forecast = getForecast(name);
        JSONArray forecastArr = (JSONArray) forecast.get("Forecasts");
        JSONObject fore = (JSONObject) forecastArr.get(0);


       ScheduledExecutorService eTP2 = Executors.newSingleThreadScheduledExecutor();
        System.out.println("Start Execution");
            eTP2.scheduleAtFixedRate(new Runnable() {
            @Override
                public void run() {



       double errors = (u.getCurrentInfo(current) - u.getForecastInfo(fore));
        errors = Math.round(errors * 100.0) / 100.0;
        
         statisticCalculator.addSpazioVaribili(errors);
            try {
                err.put("max",statisticCalculator.getMax());
            } catch (StatisticException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                err.put("min",statisticCalculator.getMin());
            } catch (StatisticException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                err.put("media",statisticCalculator.getMedia());
            } catch (StatisticException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                err.put("varianza", statisticCalculator.getVarianza());
            } catch (StatisticException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            }, 0, 1, TimeUnit.HOURS);
            
      String risultato = "Il programma salverà dati sulle statistiche sull'errore di temperatura per questa città";
        return risultato;
        }
	
	
	
	public JSONObject err =new JSONObject();


	
	/**
	 * Metodo che restituisce le statistiche sull'errore tra il forecast e l'attuale
	 */ 

	public JSONObject getStatistics() throws MalformedURLException, StatisticException, ParseException, CityNotFoundException {
	
	return err;
	};
	
	
	/**
	 * resetta l'Url prima di ogni chiamata
	 * 
	 * @author lucas
	 */
	public void ResetUrl() {
		this.url = "https://api.openweathermap.org/data/2.5/";
	}

}

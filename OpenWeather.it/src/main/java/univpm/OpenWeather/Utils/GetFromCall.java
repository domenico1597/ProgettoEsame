package univpm.OpenWeather.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import univpm.OpenWeather.Exception.CityNotFoundException;
import univpm.OpenWeather.Model.City;
import univpm.OpenWeather.Model.Position;
import univpm.OpenWeather.Model.Weather;

/**
 * Questa classe usa diversi metodi per prendere le informazioni da un
 * JSONObject e crea delle istanze delle rispettive classi.
 * 
 */
public class GetFromCall {

	/**
	 * crea una posizione da un JSONobject
	 * 
	 * @param obj JSONObject da cui prendere le informazioni di latitudine e
	 *            longitudine
	 * @return un oggetto della classe Position
	 */
	public Position createPosition(JSONObject obj) {
		JSONObject p = getCoord(obj);
		double lat = (double) p.get("lat");
		double lon = (double) p.get("lon");
		Position pos = new Position(lat, lon);
		return pos;
	}

	/**
	 * Questo metodo consente di creare una citta da un Json object
	 * 
	 * @param obj JSONObject da elaborare
	 * @return ritorna un oggetto della classe City
	 */
	public City createCity(JSONObject obj) {
		JSONObject c = getCity(obj);

		long id = (long) c.get("Id");
		String name = (String) c.get("City Name");
		Position pos = createPosition(obj);
		City city = new City(id, name, pos);

		return city;
	}

	/**
	 * questo metodo consente di creare un oggetto della classe Weather da un json
	 * object. inoltre valorizza solo il meteo oppure anche gli attributi della
	 * citta in base al parametro booleano poichè in alcuni casi gli attributi di
	 * citta rimangono gli stessi e non c'è bisogno di ricrearli.
	 * 
	 * Grazia al concatenamento con createCity e createPosition con questa classe si
	 * possono valorizzare anche gli attributi City e Position.
	 * 
	 * @param obj     Json object da elaborare
	 * @param current seleziona il tipo di operazione da fare
	 * @return un oggetto della classe Weather valorizzato con il json object
	 *         passatogli
	 * @throws CityNotFoundException
	 */
	public Weather createWeather(JSONObject obj, boolean current) throws CityNotFoundException {

		JSONObject t = (JSONObject) obj.get("main");
		JSONObject w = getWeather(obj);

		double temp = ifThenRound(t.get("temp"));
		double t_min = ifThenRound(t.get("temp_min"));
		double t_max = ifThenRound(t.get("temp_max"));

		long pressure = (long) t.get("pressure");

		String description = (String) w.get("description");
		String main = (String) w.get("main");
		long date = (long) obj.get("dt");

		if (current) {
			City city = new City();
			city = createCity(obj);
			Weather meteo = new Weather(temp, description, t_min, t_max, pressure, date, main, city);
			return meteo;
		} else {
			Weather meteo1 = new Weather(temp, description, t_min, t_max, pressure, date, main);
			return meteo1;
		}
	}

	/**
	 * @param obj     JSONObject della chiamata api
	 * @param current stabilisce se deve prendere i valori per una stampa parziale o
	 *                intera in base all'utilizzo necessario (per il forecast o il
	 *                current)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getMain(JSONObject obj, boolean current) {
		JSONObject m = (JSONObject) obj.get("main");
		JSONObject ret = new JSONObject();

		double temp = (double) m.get("temp");
		double temp_max = (double) m.get("temp_max");
		double temp_min = (double) m.get("temp_min");
		if (current) {

			ret.put("t", temp);
			ret.put("tMax", temp_max);
			ret.put("tMin", temp_min);
		} else {
			ret.put("temp", temp);
		}

		long pressure = (long) m.get("pressure");
		ret.put("pressure", pressure);

		return ret;
	}

	/**
	 * Questo metodo serve perchè alcune temperature avevano valori diversi da
	 * double che è quello usato nella classe Weather per la temperatura quindi
	 * serviva una conversione ma un semplice cast non ha funzionato.
	 * 
	 * @param number oggetto json convertito in Object questo parametro viene
	 *               convertito in Long Double o Integer in base al tipo
	 * @return restituisce il valore come tipo primitivo double
	 * 
	 */
	public double ifThenRound(Object number) {
		double ret = 0;

		if (number instanceof Double) {
			ret = (double) number;
		} else if (number instanceof Long) {
			ret = ((Long) number).doubleValue();

		} else if (number instanceof Integer) {
			ret = ((Integer) number).doubleValue();
		}

		return ret;

	}

	/**
	 * crea un JSONObject le informazioni di citta
	 * 
	 * @param obj
	 * @return json object
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCity(JSONObject obj) {
		JSONObject ret = new JSONObject();
		String name = (String) obj.get("name");// valorizza name e id
		long id = (long) obj.get("id");
		ret.put("City Name", name);
		ret.put("Id", id);

		return ret;

	}

	/**
	 * crea un jsonobject con informazioni delle coordinate
	 * 
	 * @param obj
	 * @return json object
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCoord(JSONObject obj) {
		JSONObject coordObj = (JSONObject) obj.get("coord");// valorizza lon e lat
		double lon = (double) coordObj.get("lon");
		double lat = (double) coordObj.get("lat");
		coordObj.put("lon", lon);
		coordObj.put("lat", lat);

		return coordObj;
	}

	/**
	 * 
	 * @param obj JSONobject contenente le descrizioni del meteo
	 * @return weather Un JSONObject con dentro i parametri con i nomi specificati
	 * @throws CityNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getWeather(JSONObject obj) {
		Utils u = new Utils();
		JSONArray ar = (JSONArray) obj.get("weather");
		JSONObject weather = (JSONObject) ar.get(0);
		String description = u.searchArray(obj, "weather", "description");
		String main = u.searchArray(obj, "weather", "main");
		weather.put("Main", main);
		weather.put("Description", description);

		return weather;

	}

}

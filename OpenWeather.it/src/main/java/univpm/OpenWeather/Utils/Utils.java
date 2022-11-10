package univpm.OpenWeather.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import univpm.OpenWeather.Service.WeatherImpl;

/**
 * Questa classe contiene diversi metodi utili e li raggruppa per cercare di
 * rendere più chiaro il resto del programma
 * 
 */
public class Utils {

	WeatherImpl service = new WeatherImpl();

	public Utils() {

	}

	/**
	 * Metodo che usa getCity per prendere previsioni meteo della città richiesta e
	 * restituisce il JSONArray
	 * 
	 * @return restituisce il JSONArray con la città e le relative informazioni
	 * 
	 */

	public String searchArray(JSONObject obj, String arrayName, String valueName) {
		JSONArray array = (JSONArray) obj.get(arrayName);
		Iterator<?> i = array.iterator();
		String value = "";

		while (i.hasNext()) {
			JSONObject info = (JSONObject) i.next();
			value = (String) info.get(valueName);
		}
		return value;

	}

	/**
	 * Questo metodo converte la data da epoch time ad un formato più leggibile
	 * 
	 * @param epochDate data in epoch time
	 * @return date in formato leggibile
	 * @throws ParseException
	 */
	public Date dateConverter(String dateText) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		Date date = format.parse(dateText);

		return date;
	}

	public Date toDate(long epoch) {

		Date jDate = new Date(epoch * 1000);

		return jDate;
	}

	/**
	 * converte la temperatura da Kelvin a celsius e la arrotonda
	 * 
	 * @param temp temperatura in Kelvin
	 * @return temperatura in Celsius
	 */
	public double tempConverter(double temp) {
		double t = temp - 273.15;
		double rounded = Math.round(t * 100.0) / 100.0;
		return rounded;
	}

	/**
	 * Questo metodo consente di ottenere la temperatura corrente delle previsioni
	 * del current (prende solo i valori numerici)
	 * 
	 * @param jobj JSONObject contenente le informazioni del current
	 * @return
	 */
	public double getCurrentInfo(JSONObject jobj) {
		double temp = 0;

		JSONObject tempObj = (JSONObject) jobj.get("Temperatures");

		String curr = (String) tempObj.get("Current");

		temp = Double.parseDouble(curr.substring(0, 4));
		return temp;
	}

	/**
	 * Questo metodo consente di ottenere la temperatura corrente delle previsioni
	 * del forecast (prende solo i valori numerici)
	 * 
	 * @param jobj JSONObject contenente le informazioni del forecast
	 * @return
	 */
	public double getForecastInfo(JSONObject jobj) {
		double temp = 0;

		JSONObject tempObj = (JSONObject) jobj.get("Temperatures");

		String fore = (String) tempObj.get("Current");

		temp = Double.parseDouble(fore.substring(0, 4));

		return temp;
	}

	/**
	 * Questo metodo consente di ottenere la temperatura massima delle previsioni
	 * del current (prende solo i valori numerici)
	 * 
	 * @param jobj
	 * @return
	 */
	public double getCurrentMaxTemp(JSONObject jobj) {
		double temp = 0;

		JSONObject tempObj = (JSONObject) jobj.get("Temperatures");

		String curr = (String) tempObj.get("Maximum");

		temp = Double.parseDouble(curr.substring(0, 4));
		return temp;
	}

	/**
	 * Questo metodo consente di ottenere la temperatura massima delle previsioni
	 * del forecast (prende solo i valori numerici)
	 * 
	 * @param jobj
	 * @return
	 */
	public double getForecastMaxTemp(JSONObject jobj) {
		double temp = 0;

		JSONObject tempObj = (JSONObject) jobj.get("Temperatures");

		String fore = (String) tempObj.get("Maximum");

		temp = Double.parseDouble(fore.substring(0, 4));

		return temp;
	}

	/**
	 * Questo metodo consente di ottenere la temperatura minima delle previsioni del
	 * current (prende solo i valori numerici)
	 * 
	 * @param jobj
	 * @return
	 */
	public double getCurrentMinTemp(JSONObject jobj) {
		double temp = 0;

		JSONObject tempObj = (JSONObject) jobj.get("Temperatures");

		String curr = (String) tempObj.get("Minimum");

		temp = Double.parseDouble(curr.substring(0, 4));
		return temp;
	}

	/**
	 * Questo metodo consente di ottenere la temperatura minima delle previsioni del
	 * forecast (prende solo i valori numerici)
	 * 
	 * @param jobj
	 * @return
	 */
	public double getForecastMinTemp(JSONObject jobj) {
		double temp = 0;

		JSONObject tempObj = (JSONObject) jobj.get("Temperatures");

		String fore = (String) tempObj.get("Minimum");

		temp = Double.parseDouble(fore.substring(0, 4));

		return temp;
	}
	
    /**
     * Metodo per effettuare arrotondamenti a due cifre decimali per i tipi double
     * @param valore Valore da arrotondare
     * @return the rounded value
     */
    protected double Arrotonda(double valore) {
        int arrotondamento = (int) (valore*100.0);
        return (double)arrotondamento/100;
    }

}

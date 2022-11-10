package univpm.OpenWeather.Service;

import java.net.MalformedURLException;

import java.text.ParseException;

import org.json.simple.JSONObject;

import univpm.OpenWeather.Exception.CityNotFoundException;
import univpm.OpenWeather.Model.Weather;

/**
 * Interfaccia di weatherImpl
 * 
 *
 */
public interface WeatherInt {

	public String UrlBuilder(boolean current, String cityName);

	public JSONObject getInfo(String url) throws MalformedURLException, CityNotFoundException;

	public Weather getWeather(String name) throws MalformedURLException, CityNotFoundException;

	public JSONObject getForecast(String cityName) throws MalformedURLException, ParseException, CityNotFoundException;

	public JSONObject printInfo(Weather city, boolean all);

	public void ResetUrl();

}

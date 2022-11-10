package univpm.OpenWeather.Utils;

import java.text.ParseException;

import org.json.simple.JSONObject;

import univpm.OpenWeather.Exception.ExeededDayException;
import univpm.OpenWeather.Exception.WrongDateException;

public interface FiltersInt {

	public JSONObject FromStartToFinish(String start, String finish)
			throws ParseException, ExeededDayException, WrongDateException;

	String setDate(long number) throws ParseException, ExeededDayException;

}

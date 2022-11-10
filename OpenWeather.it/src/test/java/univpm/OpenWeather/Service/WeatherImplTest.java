package univpm.OpenWeather.Service;

import java.io.FileReader;

import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import junit.framework.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Questa classe testa alcuni metodi del service
 * 
 * @author lucas
 *
 */
class WeatherImplTest extends TestCase {

	@Autowired
	private WeatherImpl service = new WeatherImpl();
	private String url = "https://api.openweathermap.org/data/2.5/";
	JSONObject esRisposta = new JSONObject();

	/**
	 * Test method for
	 * {@link univpm.OpenWeather.Service.WeatherImpl#UrlBuilder(boolean, java.lang.String)}.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@BeforeEach
	protected void setUp() throws Exception {
		System.out.println("Setting it up!");
		String json = "";
		Scanner in = new Scanner(new FileReader("risposta.txt"));
		while (in.hasNext()) {
			json += (in.nextLine());

		}

		JSONParser parser = new JSONParser();
		JSONObject obj = new JSONObject();
		obj = (JSONObject) parser.parse(json);
		this.esRisposta = obj;
		in.close();
	}

	/**
	 * verifica che l'URL per la chiamata sia costruito correttamente.
	 * 
	 * @author lucas
	 */
	@Test
	void testUrlBuilder() {
		String url = service.UrlBuilder(true, "fano");
		String urlCorretto = "https://api.openweathermap.org/data/2.5/weather?q=fano,IT&appid=15b8b402dfd9f2d93b1bfa8245d0edc6";
		assertEquals(url, urlCorretto);

	}



	/**
	 * verifica che l'URL sia resettato correttamente.
	 * 
	 * @author lucas
	 */
	@Test
	void testResetUrl() {
		service.ResetUrl();
		String url = "https://api.openweathermap.org/data/2.5/";
		assertEquals(this.url, url);
	}

	@AfterEach
	public void tearDown() {
		this.esRisposta = null;
		this.service = null;
	}
}

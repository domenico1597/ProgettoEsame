package univpm.OpenWeather.Exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.OpenWeather.Service.WeatherImpl;
import univpm.OpenWeather.Utils.GetFromCall;

/**
 * Classe che testa l'eccezione CityNotFound e EmptyStringException
 * 
 * @author Francesco
 *
 */
public class ExceptionTest {

	GetFromCall g;
	WeatherImpl i = new WeatherImpl();

	@BeforeEach
	void setUp() throws Exception {
		g = new GetFromCall();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.g = null;
	}

	@Test
	void getCurrrent() throws CityNotFoundException {
		Exception e = assertThrows(CityNotFoundException.class, () -> i.getWeather("WrongName"));
		assertEquals("City not found, please enter a different city name", e.getMessage());
	}

	@Test
	void getForecast() throws CityNotFoundException {
		Exception e = assertThrows(CityNotFoundException.class, () -> i.getForecast("WrongName"));
		assertEquals("City not found, please enter a different city name", e.getMessage());
	}


}

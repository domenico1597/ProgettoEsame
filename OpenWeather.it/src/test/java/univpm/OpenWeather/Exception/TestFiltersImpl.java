package univpm.OpenWeather.Exception;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import univpm.OpenWeather.Utils.FiltersImpl;

/**
 * Questa classe testa alcune eccezioni contenute nella classe @FiltersImpl
 * 
 * @author lucas
 */
class TestFiltersImpl {

	FiltersImpl f;

	@BeforeEach
	void setUp() throws Exception {
		f = new FiltersImpl("ancona");
	}

	@AfterEach
	void tearDown() throws Exception {
		this.f = null;
	}

	@Test
	void testSetDate1() throws ParseException, ExeededDayException {
		Exception exception = assertThrows(ExeededDayException.class, () -> f.setDate(5000000));
		assertEquals("La previsione cercata è tra più di 5 giorni da ora", exception.getMessage());
	}

	@Test
	void testSetDate2() throws ParseException, ExeededDayException {
		Exception exception = assertThrows(ExeededDayException.class, () -> f.setDate(10));
		assertEquals("La previsione cercata è tra meno di 3 ore da ora", exception.getMessage());
	}

	@Test
	void testFromStartToFinish() throws WrongDateException {
		Exception e = assertThrows(WrongDateException.class,
				() -> f.FromStartToFinish("16-01-2022 14:00", "16-01-2022 10:00"));
		assertEquals("La data di inizio è successiva alla data di fine", e.getMessage());
	}

}

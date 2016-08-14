package vs.weather;

import org.junit.Test;

import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.*;

import vs.weather.model.WeatherData;

public class WeatherDataTest {

    private static final String SAMPLE_PLACE_PATH = "Norway/Telemark/Sauherad/Gvarv";

    @Test
    public void parse_weather_data()throws Exception {
        InputStream inputStream = WeatherDataDownloader.getInputStream(SAMPLE_PLACE_PATH);
        WeatherData data = WeatherDataDeserializer.deserialize(inputStream);

        Date now = new Date();
        assertTrue(data.getMetaData().getLastUpdateTime().before(now));
        assertTrue(data.getMetaData().getNextUpdateTime().after(now));

        assertEquals("Gvarv", data.getLocation().getName());
        assertEquals("Norway", data.getLocation().getCountry());
        assertEquals("celsius", data.getForecast().getTabularForecastList().get(0).getTemperature().getUnit());
    }
}

package vs.weather;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

import vs.weather.model.WeatherData;

public class WeatherDataDeserializer {

    public static WeatherData deserialize(InputStream inputStream) throws Exception {
        Serializer serializer = new Persister();
        WeatherData weatherData = serializer.read(WeatherData.class, inputStream, false);
        return weatherData;
    }
}

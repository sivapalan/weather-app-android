package vs.weather.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class WeatherData {

    @Element
    private Location location;
    @Element
    private MetaData meta;
    @Element
    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }

    public MetaData getMetaData() {
        return meta;
    }

    public Location getLocation() {
        return location;
    }
    
}

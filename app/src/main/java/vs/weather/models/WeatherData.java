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

    private String placePath;

    public Forecast getForecast() {
        return forecast;
    }

    public MetaData getMetaData() {
        return meta;
    }

    public Location getLocation() {
        return location;
    }

    public String getPlacePath() {
        return placePath;
    }

    public void setPlacePath(String placePath) {
        this.placePath = placePath;
    }
}

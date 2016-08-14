package vs.weather.model;

import org.simpleframework.xml.Element;

public class Location {

    @Element(name = "name")
    private String name;
    @Element(name = "country")
    private String country;
    @Element(name = "location")
    private GeoLocation geoLocation;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }
}
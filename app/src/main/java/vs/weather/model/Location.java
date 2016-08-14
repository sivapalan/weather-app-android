package vs.weather.model;

import org.simpleframework.xml.Element;

public class Location {

    @Element
    private String name;
    @Element
    private String country;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
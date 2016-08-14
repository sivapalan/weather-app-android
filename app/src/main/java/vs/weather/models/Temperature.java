package vs.weather.models;

import org.simpleframework.xml.Attribute;

public class Temperature {
    @Attribute
    private String unit;
    @Attribute
    private double value;

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}

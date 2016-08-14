package vs.weather.models;

import org.simpleframework.xml.Attribute;

public class GeoLocation {

    @Attribute(name = "geobaseid")
    private long id;
    @Attribute(name = "latitude")
    private float latitude;
    @Attribute(name = "longitude")
    private float longitude;

    public long getId() {
        return id;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}

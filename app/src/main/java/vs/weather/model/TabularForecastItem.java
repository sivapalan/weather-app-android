package vs.weather.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TabularForecastItem {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Attribute
    private String from;
    @Attribute
    private String to;
    @Attribute
    private int period;

    @Element
    private Temperature temperature;
    @Element
    private Symbol symbol;

    public Date getStartTime() {
        try {
            return DATE_FORMAT.parse(from);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date getEndTime() {
        try {
            return DATE_FORMAT.parse(to);
        } catch (ParseException e) {
            return null;
        }
    }

    public int getPeriod() {
        return period;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Temperature getTemperature() {
        return temperature;
    }

}

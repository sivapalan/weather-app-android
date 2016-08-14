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

    public Date getStartTime() throws ParseException {
        return DATE_FORMAT.parse(from);
    }

    public Date getEndTime() throws ParseException {
        return DATE_FORMAT.parse(to);
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

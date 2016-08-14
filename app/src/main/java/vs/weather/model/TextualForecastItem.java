package vs.weather.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextualForecastItem {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Attribute(name = "from")
    private String date;

    @Element(name = "title")
    private String day;

    @Element(name = "body")
    private String text;

    public Date getDate() {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return null;
        }

    }

    public String getDay() {
        return day;
    }

    public String getText() {
        return text;
    }

}

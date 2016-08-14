package vs.weather.model;

import org.simpleframework.xml.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MetaData {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Element
    private String lastupdate;
    @Element
    private String nextupdate;

    public Date getLastUpdateTime() throws ParseException {
        return DATE_FORMAT.parse(lastupdate);
    }

    public Date getNextUpdateTime() throws ParseException {
        return DATE_FORMAT.parse(nextupdate);
    }
}

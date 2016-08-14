package vs.weather.models;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class TextNode {

    @ElementList(name = "location")
    private List<TextualForecastItem> textualForecastList;

    public List getTextualForecastList() {
        return textualForecastList;
    }
}

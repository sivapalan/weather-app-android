package vs.weather.model;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class TextNode {

    @ElementList(name = "location")
    private List<TextualForecastItem> textualForecastList;

    public List getTextualForecastList() {
        return textualForecastList;
    }
}

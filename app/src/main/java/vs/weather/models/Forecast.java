package vs.weather.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Forecast {

    @ElementList(name = "tabular")
    private List<TabularForecastItem> tabularForecastList;

    @Element(name = "text", required = false)
    private TextNode textNode;

    public List<TabularForecastItem> getTabularForecastList() {
        return tabularForecastList;
    }

    public List<TextualForecastItem> getTextualForecastList() {
        if (textNode == null) return null;
        return textNode.getTextualForecastList();
    }

}

package vs.weather.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Forecast {

    @ElementList(name = "tabular")
    private List<TabularForecastItem> tabularForecastList;

    @Element(name = "text")
    private TextNode textNode;

    public List<TabularForecastItem> getTabularForecastList() {
        return tabularForecastList;
    }

    public List<TextualForecastItem> getTextualForecastList() {
        return textNode.getTextualForecastList();
    }

}

package vs.weather.models;

import org.simpleframework.xml.Attribute;

public class Symbol {

    @Attribute(name = "numberEx")
    private int number;
    @Attribute(name = "name")
    private String name;
    @Attribute(name = "var")
    private String var;

    public String getVar() {
        return var;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}

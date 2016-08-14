package vs.weather.util;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUtils {

    private static final String REGEX_MOON_SYMBOLS = "mf\\/(\\d\\dn)\\.\\d\\d";

    public static int getWeatherSymbolResourceId(Context c, String symbolVar) {
        Pattern p = Pattern.compile(REGEX_MOON_SYMBOLS);
        Matcher m = p.matcher(symbolVar);
        if (m.find()) {
            symbolVar = m.group(1);
        }
        int resId = c.getResources().getIdentifier("sym_" + symbolVar, "drawable", c.getPackageName());
        return resId;
    }
}

package vs.weather.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.List;

import vs.weather.R;
import vs.weather.data.MyWeatherDataPlaces;
import vs.weather.fragments.WeatherDataDetailFragment;
import vs.weather.models.TextualForecastItem;
import vs.weather.models.WeatherData;

public class TextualForecastDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        WeatherData weatherData = null;
        Bundle args = getArguments();
        if (args != null) {
            weatherData = MyWeatherDataPlaces.getMap().get(args
                    .getLong(WeatherDataDetailFragment.ARG_ITEM_ID));
        }
        if (weatherData == null) return null;

        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        List<TextualForecastItem> forecastItems = weatherData.getForecast().getTextualForecastList();

        if (forecastItems != null) {

            WebView wv = new WebView(context);

            String htmlData = "<html><body>";
            for (TextualForecastItem forecastItem : forecastItems) {
                htmlData += "<p><h3>" + forecastItem.getDay() + "</h3>" + forecastItem.getText() + "</p>";
            }
            htmlData += "</body></html>";
            wv.loadData(htmlData, "text/html; charset=utf-8", "utf-8");

            float sizeInDp = 15;
            float scale = context.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (sizeInDp * scale + 0.5f);

            LinearLayout ll = new LinearLayout(context);
            ll.addView(wv);
            ll.setPadding(dpAsPixels, 0, dpAsPixels, 0);

            builder.setNegativeButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setTitle(weatherData.getLocation().getName())
                    .setView(ll);
        }
        return builder.create();
    }

}

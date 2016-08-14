package vs.weather;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import vs.weather.model.TabularForecastItem;
import vs.weather.model.WeatherData;
import vs.weather.util.ImageUtils;


public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {

    private List<WeatherData> mItems;

    private static final String UNICODE_DEGREE_SIGN = "\u00B0";

    public WeatherDataAdapter(List<WeatherData> items) {
        mItems = items;
        MyWeatherDataPlaces.addListener(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weatherdata_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WeatherData weatherData = mItems.get(position);
        final String name = weatherData.getLocation().getName();

        holder.titleTextView.setText(name);
        holder.setDaysWeatherData(weatherData);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, name + " (" + weatherData.getLocation().getGeoLocation().getId() + ")",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView titleTextView;
        private final TableLayout forecastTable;
        private final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE");

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.weatherdata_title);
            forecastTable = (TableLayout) itemView.findViewById(R.id.weatherdata_table);
        }

        public void setDaysWeatherData(WeatherData weatherData) {
            View[] days = new View[] {
                    forecastTable.findViewById(R.id.weatherdata_day1),
                    forecastTable.findViewById(R.id.weatherdata_day2),
                    forecastTable.findViewById(R.id.weatherdata_day3),
                    forecastTable.findViewById(R.id.weatherdata_day4),
            };
            List<TabularForecastItem> forecastItems = weatherData.getForecast().getTabularForecastList();
            int i = 0;
            View v = days[i];
            for (TabularForecastItem forecastItem : forecastItems) {
                if (forecastItem.getPeriod() == 2) {
                    // Period 2 -> Daytime, normally 12:00 - 18:00
                    // Following guideline from: http://om.yr.no/verdata/xml/spesifikasjon/
                    ImageView weatherSymbolImageView = (ImageView) v.findViewById(R.id.imageView_weatherdata_day_weather_symbol);
                    TextView weekdayTextView = (TextView) v.findViewById(R.id.textView_weatherdata_day_weekday);
                    TextView temperatureTextView = (TextView) v.findViewById(R.id.textView_weatherdata_day_temperature);

                    weatherSymbolImageView.setImageResource(ImageUtils.getWeatherSymbolResourceId(v.getContext(),
                            forecastItem.getSymbol().getVar()));

                    weekdayTextView.setText(DATE_FORMAT.format(forecastItem.getStartTime()));
                    temperatureTextView.setText(String.format("%d" + UNICODE_DEGREE_SIGN, (int) forecastItem.getTemperature().getValue()));

                    if (i < days.length - 1) v = days[++i];
                    else break;
                }
            }
        }
    }
}
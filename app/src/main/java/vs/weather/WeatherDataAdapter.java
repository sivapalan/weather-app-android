package vs.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import vs.weather.util.Globals;
import vs.weather.util.ImageUtils;


public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {

    private List<WeatherData> mItems;
    private AppCompatActivity mActivity;
    private boolean mTwoPane;

    public WeatherDataAdapter(AppCompatActivity activity, List<WeatherData> items) {
        mActivity = activity;
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
        final long id = weatherData.getLocation().getGeoLocation().getId();

        holder.titleTextView.setText(name);
        holder.setDaysWeatherData(weatherData);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putLong(WeatherDataDetailFragment.ARG_ITEM_ID, id);
                    WeatherDataDetailFragment fragment = new WeatherDataDetailFragment();
                    fragment.setArguments(arguments);
                    mActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.weatherdata_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, WeatherDataDetailActivity.class);
                    intent.putExtra(WeatherDataDetailFragment.ARG_ITEM_ID, id);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setTwoPane(boolean twoPane) {
        mTwoPane = twoPane;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView titleTextView;
        private final TableLayout forecastTable;

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

                    weekdayTextView.setText(Globals.WEEKDAY_DATE_FORMAT.format(forecastItem.getStartTime()));
                    temperatureTextView.setText(String.format("%d" + Globals.UNICODE_DEGREE_SIGN, (int) forecastItem.getTemperature().getValue()));

                    if (i < days.length - 1) v = days[++i];
                    else break;
                }
            }
        }
    }
}

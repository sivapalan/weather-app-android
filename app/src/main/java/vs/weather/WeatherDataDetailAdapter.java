package vs.weather;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vs.weather.model.TabularForecastItem;
import vs.weather.util.Globals;
import vs.weather.util.ImageUtils;

public class WeatherDataDetailAdapter extends RecyclerView.Adapter<WeatherDataDetailAdapter.ViewHolder>  {

    private List<TabularForecastItem> mList;
    private LayoutInflater mInflater;
    private Context mContext;



    public WeatherDataDetailAdapter(Context context, List<TabularForecastItem> tabularForecastItemList) {
        mContext = context;
        mList = tabularForecastItemList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weatherdata_detail_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TabularForecastItem forecastItem = mList.get(position);
        String symbolVar = forecastItem.getSymbol().getVar();

        String weekDayString = Globals.WEEKDAY_DATE_FORMAT.format(forecastItem.getStartTime());
        String timeString = Globals.TIME_DATE_FORMAT.format(forecastItem.getStartTime()) + " - \n"
                + Globals.TIME_DATE_FORMAT.format(forecastItem.getEndTime());
        holder.weekDayTextView.setText(weekDayString);
        holder.timeRangeTextView.setText(timeString);
        holder.temperatureTextView.setText(String.format("%d" + Globals.UNICODE_DEGREE_SIGN, (int) forecastItem.getTemperature().getValue()));
        holder.weatherSymbolImageView.setImageResource(ImageUtils.getWeatherSymbolResourceId(mContext, symbolVar));

        // Alternate background color for rows
        if (position % 2 == 1) holder.itemView.setBackgroundColor(Color.argb(10,0,0,0));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView weekDayTextView;
        public final TextView timeRangeTextView;
        public final TextView temperatureTextView;
        public final ImageView weatherSymbolImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            weekDayTextView = (TextView) itemView.findViewById(R.id.textView_weatherdata_detail_weekday);
            timeRangeTextView = (TextView) itemView.findViewById(R.id.textView_weatherdata_detail_timerange);
            temperatureTextView = (TextView) itemView.findViewById(R.id.textView_weatherdata_detail_temperature);
            weatherSymbolImageView = (ImageView) itemView.findViewById(R.id.imageView_weatherdata_detail_weather_symbol);
        }
    }
}

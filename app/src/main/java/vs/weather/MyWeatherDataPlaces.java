package vs.weather;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vs.weather.model.WeatherData;

public class MyWeatherDataPlaces {

    private static List<WeatherData> weatherDataList;
    private static Map<Long, WeatherData> weatherDataMap;
    public static List<RecyclerView.Adapter> listeners;

    static {
        weatherDataMap = new HashMap<>();
        weatherDataList = new ArrayList<>();
        listeners = new ArrayList<>();
        addSamplePlaces();
    }

    public static Map<Long, WeatherData> getMap() {
        return weatherDataMap;
    }

    public static List<WeatherData> getList() {
        return weatherDataList;
    }

    public static void add(WeatherData weatherData){
        weatherDataList.add(weatherData);
        weatherDataMap.put(weatherData.getLocation().getGeoLocation().getId(), weatherData);
        notifyListeners();
    }

    public static void addListener(RecyclerView.Adapter adapter) {
        listeners.add(adapter);
    }

    public static void removeListener(RecyclerView.Adapter adapter) {
        listeners.remove(adapter);
    }

    private static void notifyListeners() {
        for (RecyclerView.Adapter adapter : listeners) {
            adapter.notifyDataSetChanged();
        }
    }

    private static void addSamplePlaces() {
        String[] places = new String[]{
                "Norway/Telemark/Sauherad/Gvarv",
                "USA/New_York/New_York",
                "Norway/Oslo/Oslo/Oslo",
                "Norway/Oslo/Oslo/Blindern",
                "Japan/Tokyo/Tokyo"
        };

        for (String place : places) {
            WeatherDataHandler wdh = new WeatherDataHandler(place, new WeatherDataHandler.ICallback() {
                @Override
                public void call(WeatherData weatherData) {
                    if (weatherData != null) add(weatherData);
                }
            });
            wdh.fetch();
        }
    }
}

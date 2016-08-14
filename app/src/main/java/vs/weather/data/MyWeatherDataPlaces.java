package vs.weather.data;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vs.weather.services.WeatherDataHandler;
import vs.weather.models.WeatherData;

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
        long id = weatherData.getLocation().getGeoLocation().getId();
        if (weatherDataMap.containsKey(id)) {
            for (int i = 0; i < weatherDataList.size(); i++) {
                if (weatherDataList.get(i).getLocation().getGeoLocation().getId() == id) {
                    weatherDataList.set(i, weatherData);
                    break;
                }
            }
        } else {
            weatherDataList.add(weatherData);
        }
        weatherDataMap.put(weatherData.getLocation().getGeoLocation().getId(), weatherData);
        notifyListeners();
    }

    private static int mostRecentlyRemovedIndex = -1;
    private static WeatherData mostRecentlyRemovedWeatherData;

    public static WeatherData remove(WeatherData weatherData) {
        int index = weatherDataList.indexOf(weatherData);
        if (index >= 0) {
            weatherDataList.remove(index);
            weatherDataMap.remove(weatherData.getLocation().getGeoLocation().getId());
            mostRecentlyRemovedIndex = index;
            mostRecentlyRemovedWeatherData = weatherData;
            notifyListeners();
            return  weatherData;
        }
        return null;
    }

    public static boolean undoLastRemove() {
        if (mostRecentlyRemovedIndex >= 0) {
            getList().add(mostRecentlyRemovedIndex, mostRecentlyRemovedWeatherData);
            getMap().put(mostRecentlyRemovedWeatherData.getLocation().getGeoLocation().getId(),
                    mostRecentlyRemovedWeatherData);
            mostRecentlyRemovedIndex = -1;
            mostRecentlyRemovedWeatherData = null;
            notifyListeners();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a new place and fetches its weather data.
     * @param placePath Path to place.Examples: "<i>Norway/Telemark/Sauherad/Gvarv</i>",
     *                  "<i>USA/New_York/New_York"</i>".
     * @see WeatherData
     */
    public static void addPlace(String placePath) {
        WeatherDataHandler wdh = new WeatherDataHandler(placePath, new WeatherDataHandler.ICallback() {
            @Override
            public void call(WeatherData weatherData) {
                if (weatherData != null) add(weatherData);
            }
        });
        wdh.fetch();
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

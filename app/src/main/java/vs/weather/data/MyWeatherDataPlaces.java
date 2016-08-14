package vs.weather.data;

import android.support.annotation.Nullable;
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
    private static List<RecyclerView.Adapter> listeners;

    private static int mostRecentlyRemovedIndex = -1;
    private static WeatherData mostRecentlyRemovedWeatherData;

    static {
        weatherDataList = new ArrayList<>();
        weatherDataMap = new HashMap<>();
        listeners = new ArrayList<>();
        loadPlaces();
        refreshAll(null);
    }

    public static Map<Long, WeatherData> getMap() {
        return weatherDataMap;
    }

    public static List<WeatherData> getList() {
        return weatherDataList;
    }

    private static void add(WeatherData weatherData){
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
        weatherDataMap.put(id, weatherData);
        notifyListeners();
        savePlaces();
    }

    public static WeatherData remove(WeatherData weatherData) {
        int index = weatherDataList.indexOf(weatherData);
        if (index >= 0) {
            weatherDataList.remove(index);
            weatherDataMap.remove(weatherData.getLocation().getGeoLocation().getId());
            mostRecentlyRemovedIndex = index;
            mostRecentlyRemovedWeatherData = weatherData;
            notifyListeners();
            savePlaces();
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
            savePlaces();
            return true;
        } else {
            return false;
        }
    }

    public static void refreshAll(WeatherDataHandler.ICallback callback) {
        int n = weatherDataList.size();
        for (int i = 0; i < n; i++) {
            final WeatherData wd = weatherDataList.get(i);
            if (i == n - 1) addPlace(wd.getPlacePath(), callback);
            else addPlace(wd.getPlacePath(), null);
        }
    }

    /**
     * Adds a new place and fetches its weather data.
     * @param placePath Path to place.Examples: "<i>Norway/Telemark/Sauherad/Gvarv</i>",
     *                  "<i>USA/New_York/New_York"</i>".
     * @param callback  An object implementing {@link vs.weather.services.WeatherDataHandler.ICallback}
     *                  containing code to be executed after data has been fetched.
     * @see WeatherData
     * @see vs.weather.services.WeatherDataHandler.ICallback
     */
    public static void addPlace(final String placePath, @Nullable final WeatherDataHandler.ICallback callback) {
        WeatherDataHandler wdh = new WeatherDataHandler(placePath, new WeatherDataHandler.ICallback() {
            @Override
            public void call(WeatherData weatherData) {
                if (weatherData != null) {
                    weatherData.setPlacePath(placePath);
                    add(weatherData);
                    if (callback != null) callback.call(weatherData);
                }
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

    private static void loadPlaces() {
        List<WeatherData> storedWeatherDataList = PersistentStorageManager.loadWeatherDataList();
        if (storedWeatherDataList == null) {
            addSamplePlaces();
        } else {
            for (WeatherData weatherData : storedWeatherDataList) {
                add(weatherData);
            }
        }
    }

    private static void savePlaces() {
        PersistentStorageManager.saveWeatherDataList(getList());
    }

    private static void addSamplePlaces() {
        String[] places = new String[]{
                "Norway/Telemark/Sauherad/Gvarv",
                "USA/New_York/New_York",
                "Norway/Oslo/Oslo/Oslo",
                "Norway/Oslo/Oslo/Blindern",
                "Japan/Tokyo/Tokyo"
        };

        for (final String place : places) {
            addPlace(place, null);
        }
    }
}

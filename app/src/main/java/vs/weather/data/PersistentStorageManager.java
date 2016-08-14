package vs.weather.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import vs.weather.models.WeatherData;

public class PersistentStorageManager {

    private static SharedPreferences sharedPreferences;

    private static final String WEATHERDATA_ARRAY_KEY = "WeatherData_Array";

    public static void init(Context c){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static void saveWeatherDataList(List<WeatherData> weatherDataList) {
        WeatherData[] weatherDataArray = new WeatherData[weatherDataList.size()];
        weatherDataList.toArray(weatherDataArray); // Fill the array

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String json = new Gson().toJson(weatherDataArray, WeatherData[].class);
        prefsEditor.putString(WEATHERDATA_ARRAY_KEY, json);
        prefsEditor.commit();
    }

    public static List<WeatherData> loadWeatherDataList() {
        String storedJson = sharedPreferences.getString(WEATHERDATA_ARRAY_KEY, "");
        if (storedJson.isEmpty()) {
            return null;
        } else {
            WeatherData[] array = new Gson().fromJson(storedJson, WeatherData[].class);
            return Arrays.asList(array);
        }
    }

}

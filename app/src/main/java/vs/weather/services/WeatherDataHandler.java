package vs.weather.services;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import vs.weather.models.WeatherData;

public class WeatherDataHandler {

    private static final String LOG_TAG = "VS";

    private ICallback callback;
    private String placePath;

    public WeatherDataHandler(String placePath, ICallback callback){
        this.placePath = placePath;
        this.callback = callback;
    }

    public void fetch(){
        new DownloadAndDeserializeXmlTask().execute(this.placePath);
    }

    // Implementation of AsyncTask used to download and deserialize XML
    private class DownloadAndDeserializeXmlTask extends AsyncTask<String, Void, WeatherData> {
        @Override
        protected WeatherData doInBackground(String... urls) {
            try {
                InputStream inputStream = WeatherDataDownloader.getInputStream(urls[0]);
                return WeatherDataDeserializer.deserialize(inputStream);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Downloading Error", e);
                return null;
            }
            catch (Exception e) {
                Log.e(LOG_TAG, "Parsing Error", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(WeatherData result) {
            if (callback != null) callback.call(result);
        }
    }

    public interface ICallback {
        void call(WeatherData weatherData);
    }
}

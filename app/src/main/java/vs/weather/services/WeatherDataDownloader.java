package vs.weather.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDataDownloader {

    private static String getAbsoluteUrl(String placePath){
        return "http://www.yr.no/place/" + placePath + "/forecast.xml";
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private static InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000); // milliseconds
        conn.setConnectTimeout(15000); // milliseconds
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect(); // Starts the query
        return conn.getInputStream();
    }

    public static InputStream getInputStream(String placePath) throws IOException {
        return downloadUrl(getAbsoluteUrl(placePath));
    }
}

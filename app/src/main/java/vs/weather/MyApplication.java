package vs.weather;

import android.app.Application;

import vs.weather.data.PersistentStorageManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PersistentStorageManager.init(getApplicationContext());
    }
}

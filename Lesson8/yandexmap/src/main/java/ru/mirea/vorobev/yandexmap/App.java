package ru.mirea.vorobev.yandexmap;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private final String MAPKIT_API_KEY = "a84007cd-0b13-446c-8227-339ac0925864";

    @Override
    public void onCreate() {
        super.onCreate();
// Set the api key before calling initialize on MapKitFactory.
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}
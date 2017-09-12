package net.jasin.eliza.beritaboard;

import android.app.Application;
import android.content.Context;

/**
 * Created by elizajasin on 12/09/2017.
 */

public class MyApplication extends Application {

    public static final String API_KEY = "fa055fcd23e44398b97ca647ed234c5f";
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}

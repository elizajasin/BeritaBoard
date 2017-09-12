package net.jasin.eliza.beritaboard.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.jasin.eliza.beritaboard.MyApplication;

/**
 * Created by elizajasin on 12/09/2017.
 */

public class VolleySingleton {
    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getsInstance(){
        if (sInstance == null){
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
}

package net.jasin.eliza.beritaboard;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by elizajasin on 12/09/2017.
 */

public class Logging {
    public static void m(String message){
        Log.d("elizajasin", "" + message);
    }

    public static void t(Context context, String message){
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
}

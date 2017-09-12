package net.jasin.eliza.beritaboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import net.jasin.eliza.beritaboard.network.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSources adapterSources;

    private final String TAG = "Home";

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(10), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logging.t(Home.this, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerv_sources);
        adapterSources = new AdapterSources(this,getData());
        recyclerView.setAdapter(adapterSources);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    public static String getRequestUrl(int limit){
        return "https://newsapi.org/v1/sources?language=en&apiKey="+MyApplication.API_KEY+"&limit="+limit;
    }

    public static List<NewsSources> getData(){
        List<NewsSources> data = new ArrayList<>();
        int[] icons = {R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb};
        String[] titles = {"Eliza", "Riviera", "Rachmawati","Eliza", "Riviera", "Rachmawati","Eliza", "Riviera", "Rachmawati"};

        for (int i = 0; i < titles.length && i < icons.length; i++){
            NewsSources current = new NewsSources();
            current.iconId = icons[i];
            current.title = titles[i];
            current.category = titles[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about){
            Toast toast = Toast.makeText(this, "Developed by\nEliza Riviera Rachmawati Jasin\neliza@jasin.net", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
        }
        return true;
    }
}

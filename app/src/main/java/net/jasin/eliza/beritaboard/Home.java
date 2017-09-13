package net.jasin.eliza.beritaboard;

import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import net.jasin.eliza.beritaboard.network.Keys;
import net.jasin.eliza.beritaboard.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
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
    private ArrayList<NewsSources> lisSources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();

        sendJsonRequest();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerv_sources);
        adapterSources = new AdapterSources(this);
        recyclerView.setAdapter(adapterSources);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void sendJsonRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Url : " + getRequestUrl());
                lisSources = parseJSONResponse(response);
                adapterSources.setListSources(lisSources);
                Log.d(TAG, "List source : " + lisSources.toString());
                Log.d(TAG, "Lenght source : " + lisSources.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private ArrayList<NewsSources> parseJSONResponse(JSONObject response){
        ArrayList<NewsSources> listMedia = new ArrayList<>();
        if (response != null || response.length() > 0){
            try {
                JSONArray arraySource = response.getJSONArray(Keys.EndPointNews.KEY_SOURCES);
                for (int i = 0; i < arraySource.length(); i++){
                    JSONObject currentSource = arraySource.getJSONObject(i);
                    String name = currentSource.getString("name");
                    String category = currentSource.getString("category");
                    String description = currentSource.getString("description");

                    Log.d(TAG, "Name : " + name);
                    Log.d(TAG, "Category : " + category);
                    Log.d(TAG, "Description : " + description);

                    NewsSources sources = new NewsSources();
                    sources.setName(name);
                    sources.setCategory(category);
                    sources.setDescription(description);
                    Log.d(TAG, "Get source : " + sources.getName());
                    listMedia.add(sources);
                }
            } catch (JSONException e){

            } catch (ParseException e){

            }
        }
        return listMedia;
    }

    public static String getRequestUrl(){
        return "https://newsapi.org/v1/sources?language=en&apiKey="+MyApplication.API_KEY;
    }

//    public static List<NewsSources> getData(){
//        List<NewsSources> data = new ArrayList<>();
//        int[] icons = {R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb};
//        String[] titles = {"Eliza", "Riviera", "Rachmawati","Eliza", "Riviera", "Rachmawati","Eliza", "Riviera", "Rachmawati"};
//
//        for (int i = 0; i < titles.length && i < icons.length; i++){
//            NewsSources current = new NewsSources();
//            current.iconId = icons[i];
//            current.title = titles[i];
//            current.category = titles[i];
//            data.add(current);
//        }
//        return data;
//    }

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

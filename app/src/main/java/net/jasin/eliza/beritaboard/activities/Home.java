package net.jasin.eliza.beritaboard.activities;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.jasin.eliza.beritaboard.R;
import net.jasin.eliza.beritaboard.adapter.AdapterSources;
import net.jasin.eliza.beritaboard.information.NewsSources;
import net.jasin.eliza.beritaboard.network.MyApplication;
import net.jasin.eliza.beritaboard.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static net.jasin.eliza.beritaboard.information.Keys.EndPointNews.*;
import static net.jasin.eliza.beritaboard.information.UrlEndPoints.*;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSources adapterSources;

    private final String TAG = "Home";

    private VolleySingleton volleySingleton;
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(getIntent().getStringExtra("lang")), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Url : " + getRequestUrl(getIntent().getStringExtra("lang")));
                lisSources = parseJSONResponse(response);
                adapterSources.setListSources(lisSources);
                Log.d(TAG, "List source : " + lisSources.toString());
                Log.d(TAG, "Lenght source : " + lisSources.size());
                if (lisSources.size() == 0){
                    Toast toast = Toast.makeText(Home.this, "Sources in this language is empty", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<NewsSources> parseJSONResponse(JSONObject response){
        ArrayList<NewsSources> listMedia = new ArrayList<>();
        if (response != null || response.length() > 0){
            try {
                JSONArray arraySource = response.getJSONArray(KEY_SOURCES);
                for (int i = 0; i < arraySource.length(); i++){
                    JSONObject currentSource = arraySource.getJSONObject(i);
                    String id = currentSource.getString(KEY_ID);
                    String name = currentSource.getString(KEY_NAME);
                    String category = currentSource.getString(KEY_CATEGORY);
                    String description = currentSource.getString(KEY_DESCRIPTION);

                    NewsSources sources = new NewsSources();
                    sources.setId(id);
                    sources.setName(name);
                    sources.setCategory(category);
                    sources.setDescription(description);
                    listMedia.add(sources);
                }
            } catch (JSONException e){
                Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            } catch (ParseException e){
                Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        return listMedia;
    }

    public static String getRequestUrl(String lang){
        return URL_SOURCES+URL_CHAR_QUESTION+URL_PARAM_LANGUAGE+lang+URL_CHAR_AMEPERSAND+URL_PARAM_API_KEY+MyApplication.API_KEY;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);

        MenuItem top = menu.findItem(R.id.menu_top);
        top.setVisible(false);

        MenuItem latest = menu.findItem(R.id.menu_latest);
        latest.setVisible(false);

        MenuItem popular = menu.findItem(R.id.menu_popular);
        popular.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_en){
            Intent intent = new Intent(Home.this, Home.class);
            intent.putExtra("lang", "en");
            startActivity(intent);
        }
        if (id == R.id.menu_de){
            Intent intent = new Intent(Home.this, Home.class);
            intent.putExtra("lang", "de");
            startActivity(intent);
        }
        if (id == R.id.menu_fr){
            Intent intent = new Intent(Home.this, Home.class);
            intent.putExtra("lang", "fr");
            startActivity(intent);
        }
        if (id == R.id.menu_about){
            Toast toast = Toast.makeText(this, "Developed by\nEliza Riviera Rachmawati Jasin\neliza@jasin.net", Toast.LENGTH_SHORT);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if( v != null) v.setGravity(Gravity.CENTER);
            toast.show();
        }
        return true;
    }
}

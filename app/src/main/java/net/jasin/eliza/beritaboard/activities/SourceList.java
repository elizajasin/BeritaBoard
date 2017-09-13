package net.jasin.eliza.beritaboard.activities;

import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.android.volley.toolbox.JsonObjectRequest;

import net.jasin.eliza.beritaboard.R;
import net.jasin.eliza.beritaboard.adapter.AdapterArticles;
import net.jasin.eliza.beritaboard.information.NewsArticles;
import net.jasin.eliza.beritaboard.network.MyApplication;
import net.jasin.eliza.beritaboard.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static net.jasin.eliza.beritaboard.information.Keys.EndPointNews.*;
import static net.jasin.eliza.beritaboard.information.UrlEndPoints.*;

public class SourceList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterArticles adapterArticles;

    private final String TAG = "SourceList";

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<NewsArticles> listArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_list);

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();

        sendJsonRequest();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerv_articles);
        adapterArticles = new AdapterArticles(this);
        recyclerView.setAdapter(adapterArticles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendJsonRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Url : " + getRequestUrl());
                listArticles = parseJSONResponse(response);
                adapterArticles.setListArticles(listArticles);
                Log.d(TAG, "List source : " + listArticles.toString());
                Log.d(TAG, "Lenght source : " + listArticles.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private ArrayList<NewsArticles> parseJSONResponse(JSONObject response){
        ArrayList<NewsArticles> listMedia = new ArrayList<>();
        if (response != null || response.length() > 0){
            try {
                JSONArray arrayArticles = response.getJSONArray(KEY_ARTICLES);
                for (int i = 0; i < arrayArticles.length(); i++){
                    JSONObject currentSource = arrayArticles.getJSONObject(i);
                    String title = currentSource.getString(KEY_TITLE);
                    String image = currentSource.getString(KEY_IMAGE);

                    NewsArticles article = new NewsArticles();
                    article.setTitle(title);
                    article.setImage(image);
                    listMedia.add(article);
                }
            } catch (JSONException e){

            } catch (ParseException e){

            }
        }
        return listMedia;
    }

    public static String getRequestUrl(){
        return URL_ARTICLES+URL_CHAR_QUESTION+URL_PARAM_SOURCE+"the-next-web"+URL_CHAR_AMEPERSAND+URL_PARAM_SORT_BY+MyApplication.LATEST+URL_CHAR_AMEPERSAND+URL_PARAM_API_KEY+MyApplication.API_KEY;
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

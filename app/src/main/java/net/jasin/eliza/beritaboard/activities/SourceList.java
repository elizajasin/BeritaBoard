package net.jasin.eliza.beritaboard.activities;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
    private SearchView mnSearch;

    private final String TAG = "SourceList";

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<NewsArticles> listArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_list);

        setTitle(getIntent().getStringExtra("name"));

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();

        sendJsonRequest(getIntent().getStringExtra("query"));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerv_articles);
        adapterArticles = new AdapterArticles(this);
        recyclerView.setAdapter(adapterArticles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendJsonRequest(final String query){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(getIntent().getStringExtra("id"),getIntent().getStringExtra("sort")), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Url : " + getRequestUrl(getIntent().getStringExtra("id"),getIntent().getStringExtra("sort")));
                listArticles = parseJSONResponse(response, query);
                adapterArticles.setListArticles(listArticles);
                Log.d(TAG, "List source : " + listArticles.toString());
                Log.d(TAG, "Lenght source : " + listArticles.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(SourceList.this, "The news source you've selected from "+ getIntent().getStringExtra("name") + " isn't available sorted by " + getIntent().getStringExtra("sort"), Toast.LENGTH_LONG);
                toast.show();
            }
        });
        requestQueue.add(request);
    }

    private ArrayList<NewsArticles> parseJSONResponse(JSONObject response, String query){
        ArrayList<NewsArticles> listMedia = new ArrayList<>();
        if (response != null || response.length() > 0){
            try {
                JSONArray arrayArticles = response.getJSONArray(KEY_ARTICLES);
                if (query != ""){
                    for (int i = 0; i < arrayArticles.length(); i++){
                        JSONObject currentSource = arrayArticles.getJSONObject(i);
                        String title = currentSource.getString(KEY_TITLE);
                        String image = currentSource.getString(KEY_IMAGE);
                        String urlArticle = currentSource.getString(KEY_URL);
                        String source = getIntent().getStringExtra("name");
                        String publishAt = currentSource.getString(KEY_DATE);
                        String[] split1 = publishAt.split("T");
                        String[] split2 = split1[1].split("Z");
                        String date = split1[0];
                        String time = split2[0];

                        NewsArticles article = new NewsArticles();
                        article.setTitle(title);
                        article.setImage(image);
                        article.setUrlArticle(urlArticle);
                        article.setSource(source);
                        article.setDate(date);
                        article.setTime(time);

                        if (title.toUpperCase().contains(query.toUpperCase())) {
                            listMedia.add(article);
                        }
                    }
                } else {
                    for (int i = 0; i < arrayArticles.length(); i++){
                        JSONObject currentSource = arrayArticles.getJSONObject(i);
                        String title = currentSource.getString(KEY_TITLE);
                        String image = currentSource.getString(KEY_IMAGE);
                        String urlArticle = currentSource.getString(KEY_URL);
                        String source = getIntent().getStringExtra("name");
                        String publishAt = currentSource.getString(KEY_DATE);
                        String[] split1 = publishAt.split("T");
                        String[] split2 = split1[1].split("Z");
                        String date = split1[0];
                        String time = split2[0];

                        NewsArticles article = new NewsArticles();
                        article.setTitle(title);
                        article.setImage(image);
                        article.setUrlArticle(urlArticle);
                        article.setSource(source);
                        article.setDate(date);
                        article.setTime(time);
                    }
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

    public static String getRequestUrl(String id, String sort){
        return URL_ARTICLES+URL_CHAR_QUESTION+URL_PARAM_SOURCE+id+URL_CHAR_AMEPERSAND+URL_PARAM_SORT_BY+sort+URL_CHAR_AMEPERSAND+URL_PARAM_API_KEY+MyApplication.API_KEY;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem en = menu.findItem(R.id.menu_en);
        en.setVisible(false);

        MenuItem de = menu.findItem(R.id.menu_de);
        de.setVisible(false);

        MenuItem fr = menu.findItem(R.id.menu_fr);
        fr.setVisible(false);

        mnSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(SourceList.this, SourceList.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("query", query);
                intent.putExtra("sort", getIntent().getStringExtra("sort"));
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_top){
            Intent intent = new Intent(SourceList.this, SourceList.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            intent.putExtra("name", getIntent().getStringExtra("name"));
            intent.putExtra("query", getIntent().getStringExtra("query"));
            intent.putExtra("sort", "top");
            startActivity(intent);
        }
        if (id == R.id.menu_latest){
            Intent intent = new Intent(SourceList.this, SourceList.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            intent.putExtra("name", getIntent().getStringExtra("name"));
            intent.putExtra("query", getIntent().getStringExtra("query"));
            intent.putExtra("sort", "latest");
            startActivity(intent);
        }
        if (id == R.id.menu_popular){
            Intent intent = new Intent(SourceList.this, SourceList.class);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            intent.putExtra("name", getIntent().getStringExtra("name"));
            intent.putExtra("query", getIntent().getStringExtra("query"));
            intent.putExtra("sort", "popular");
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

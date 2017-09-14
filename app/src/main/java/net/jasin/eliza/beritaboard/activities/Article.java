package net.jasin.eliza.beritaboard.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import net.jasin.eliza.beritaboard.R;

public class Article extends AppCompatActivity {

    private final String TAG = "Article";

    WebView webView;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        setTitle(getIntent().getStringExtra("source"));

        webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.loadUrl(getIntent().getStringExtra("url"));
        Log.d(TAG, "URL : " + getIntent().getStringExtra("url"));
        webView.setWebViewClient(new WebViewClient());
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

        MenuItem en = menu.findItem(R.id.menu_en);
        en.setVisible(false);

        MenuItem de = menu.findItem(R.id.menu_de);
        de.setVisible(false);

        MenuItem fr = menu.findItem(R.id.menu_fr);
        fr.setVisible(false);

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

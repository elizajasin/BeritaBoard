package net.jasin.eliza.beritaboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSources adapterSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerv_sources);
        adapterSources = new AdapterSources(this,getData());
        recyclerView.setAdapter(adapterSources);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    public static List<NewsSources> getData(){
        List<NewsSources> data = new ArrayList<>();
        int[] icons = {R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb,R.drawable.logo_bb};
        String[] titles = {"Eliza", "Riviera", "Rachmawati","Eliza", "Riviera", "Rachmawati","Eliza", "Riviera", "Rachmawati"};

        for (int i = 0; i < titles.length && i < icons.length; i++){
            NewsSources current = new NewsSources();
            current.iconId = icons[i];
            current.title = titles[i];
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

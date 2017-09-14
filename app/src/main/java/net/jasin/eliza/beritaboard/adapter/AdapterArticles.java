package net.jasin.eliza.beritaboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import net.jasin.eliza.beritaboard.activities.Article;
import net.jasin.eliza.beritaboard.activities.SourceList;
import net.jasin.eliza.beritaboard.information.NewsArticles;
import net.jasin.eliza.beritaboard.R;
import net.jasin.eliza.beritaboard.network.VolleySingleton;

import java.util.ArrayList;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by elizajasin on 13/09/2017.
 */

public class AdapterArticles extends RecyclerView.Adapter<AdapterArticles.MyViewHolder>{
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private ArrayList<NewsArticles> listArticles = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    private final String TAG = "AdapterArticles";

    public AdapterArticles(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setListArticles(ArrayList<NewsArticles> listArticle){
        this.listArticles = listArticle;
        Log.d(TAG, "Size : " + listArticles.size());
        notifyItemChanged(0, listArticles.size());
    }

    @Override
    public AdapterArticles.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.articles_row, parent, false);
        AdapterArticles.MyViewHolder holder = new AdapterArticles.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AdapterArticles.MyViewHolder holder, int position) {
        NewsArticles currentArticle = listArticles.get(position);
        Log.d(TAG, "Position : " + listArticles.toString());
        holder.title.setText(currentArticle.getTitle());
        String urlImage = currentArticle.getImage();
        if(urlImage != null){
            imageLoader.get(urlImage, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        holder.url = listArticles.get(position).getUrlArticle();
        holder.source = listArticles.get(position).getSource();

        String tgl = listArticles.get(position).getDate();
        String wkt = listArticles.get(position).getTime();
        holder.date.setText("Published at : "+tgl+" "+wkt);
    }

    @Override
    public int getItemCount() {
        return listArticles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView image;
        private TextView date;
        private String url;
        private String source;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.text_article);
            date = (TextView) itemView.findViewById(R.id.text_date);
            image = (ImageView) itemView.findViewById(R.id.img_article);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, Article.class);
            intent.putExtra("url", url);
            intent.putExtra("source", source);
            context.startActivity(intent);
        }
    }
}

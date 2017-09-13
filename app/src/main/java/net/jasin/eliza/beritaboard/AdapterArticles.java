package net.jasin.eliza.beritaboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import net.jasin.eliza.beritaboard.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by elizajasin on 13/09/2017.
 */

public class AdapterArticles extends RecyclerView.Adapter<AdapterArticles.MyViewHolder>{
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private ArrayList<NewsArticles> listArticles = new ArrayList<>();
    private LayoutInflater inflater;

    public AdapterArticles(Context context){
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setListArticles(ArrayList<NewsArticles> listArticle){
        this.listArticles = listArticle;
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
    }

    @Override
    public int getItemCount() {
        return listArticles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.text_article);
            image = (ImageView) itemView.findViewById(R.id.img_article);
        }

//        @Override
//        public void onClick(View view) {
//
//            view.getContext().startActivity(i);
//            context.startActivity(new Intent(context, SourceList.class));
//        }
    }
}

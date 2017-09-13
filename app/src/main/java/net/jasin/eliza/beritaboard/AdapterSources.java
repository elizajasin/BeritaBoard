package net.jasin.eliza.beritaboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.jasin.eliza.beritaboard.network.VolleySingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by elizajasin on 12/09/2017.
 */

public class AdapterSources extends RecyclerView.Adapter<AdapterSources.MyViewHolder>{

    private VolleySingleton volleySingleton;
    private ArrayList<NewsSources> listSources = new ArrayList<>();
    private LayoutInflater inflater;

    public AdapterSources(Context context){
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
    }

    public void setListSources(ArrayList<NewsSources> listSources){
        this.listSources = listSources;
        notifyItemChanged(0, listSources.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sources_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsSources currentSource = listSources.get(position);
        holder.title.setText(currentSource.getName());
        holder.category.setText(currentSource.getCategory());
        holder.description.setText(currentSource.getDescription());
    }

    @Override
    public int getItemCount() {
        return listSources.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView category;
        private TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.text_sources);
            category = (TextView) itemView.findViewById(R.id.text_category);
            description = (TextView) itemView.findViewById(R.id.text_desc);
        }

//        @Override
//        public void onClick(View view) {
//            context.startActivity(new Intent(context, SourceList.class));
//        }
    }
}
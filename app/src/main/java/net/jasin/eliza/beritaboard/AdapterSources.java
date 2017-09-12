package net.jasin.eliza.beritaboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by elizajasin on 12/09/2017.
 */

public class AdapterSources extends RecyclerView.Adapter<AdapterSources.MyViewHolder>{

    private LayoutInflater inflater;
    List<NewsSources> data = Collections.emptyList();
    private Context context;

    public AdapterSources(Context context, List<NewsSources> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sources_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsSources current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
        holder.category.setText(current.category);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView category;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.text_sources);
            icon = (ImageView) itemView.findViewById(R.id.image_sources);
            category = (TextView) itemView.findViewById(R.id.text_category);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, SourceList.class));
        }
    }
}
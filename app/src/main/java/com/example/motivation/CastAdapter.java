package com.example.motivation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CastAdapter  extends RecyclerView.Adapter<CastAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Cast> casts;

    public CastAdapter(Context context, ArrayList<Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_card, viewGroup, false);
        return new CastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       Cast cast = casts.get(i);
        viewHolder.cast_name.setText(cast.getName());
       viewHolder.character.setText(cast.getCharacter());
       if(cast.getProfile_url()!=null) Glide.with(context).load("http://image.tmdb.org/t/p/w300"+cast.getProfile_url()).into(viewHolder.cast_poster);
    }

    @Override
    public int getItemCount() {
       if(casts == null) return 0;
       else return casts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cast_poster;
        TextView character;
        TextView cast_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cast_poster = itemView.findViewById(R.id.cast_card_profile);
            character = itemView.findViewById(R.id.character_name);
           cast_name = itemView.findViewById(R.id.original_cast_name);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(v.getContext(),CastDetail.class);
                   Cast cast = casts.get(getAdapterPosition());
                   long cast_id = cast.getId();
                   intent.putExtra("cast_id",cast_id);
                    v.getContext().startActivity(intent);
               }
           });
        }
    }
}

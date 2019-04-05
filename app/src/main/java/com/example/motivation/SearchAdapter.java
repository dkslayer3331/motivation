package com.example.motivation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private ArrayList<Movie> search_movies;
    private Context context;

    public SearchAdapter(ArrayList<Movie> search_movies, Context context) {
        this.search_movies = search_movies;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_movie_item, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        if(search_movies.get(i).getImg_url()!=null)
        Glide.with(context).load("https://image.tmdb.org/t/p/w154"+search_movies.get(i).getImg_url()).into(searchViewHolder.search_movie_poster);
    }

    @Override
    public int getItemCount() {
        if(search_movies == null) return 0;
        else return search_movies.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView search_movie_poster;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            search_movie_poster = itemView.findViewById(R.id.search_movie_poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),DetailedInfo.class);
                    intent.putExtra("detailed",search_movies.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}

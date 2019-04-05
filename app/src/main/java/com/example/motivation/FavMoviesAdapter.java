package com.example.motivation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.FavMoviesViewHolder>{

    private Context context;
    private List<FavMovieObj> all_fav_movies;

    public FavMoviesAdapter(Context context, List<FavMovieObj> all_fav_movies) {
        this.context = context;
        this.all_fav_movies = all_fav_movies;
    }

    @NonNull
    @Override
    public FavMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_fav_movies, viewGroup, false);
        return new FavMoviesAdapter.FavMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMoviesViewHolder favMoviesViewHolder, int i) {
            favMoviesViewHolder.fav_movies_genres.setText(all_fav_movies.get(i).getGenre());
            favMoviesViewHolder.fav_movies_title.setText(all_fav_movies.get(i).getTitle());
            Glide.with(context).load("https://image.tmdb.org/t/p/w300"+all_fav_movies.get(i).getImg_url()).into(favMoviesViewHolder.fav_movies_poster);
    }

    @Override
    public int getItemCount() {
       if(all_fav_movies==null) return 0;
       else return all_fav_movies.size();
    }

    public class FavMoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView fav_movies_poster;
        TextView fav_movies_title,fav_movies_genres;

        public FavMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_movies_poster = itemView.findViewById(R.id.fav_movie_poster);
            fav_movies_title = itemView.findViewById(R.id.fav_movie_title);
            fav_movies_genres = itemView.findViewById(R.id.fav_movie_genre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long selected_movie = all_fav_movies.get(getAdapterPosition()).getMovie_id();
                    Intent intent = new Intent(v.getContext(),DetailedInfo.class);
                    intent.putExtra("detailed", selected_movie);
                    Log.d("fav clicked movie",selected_movie+"");
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}

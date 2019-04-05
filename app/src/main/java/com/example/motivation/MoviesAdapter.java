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

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    public MoviesAdapter(Context context,ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    private ArrayList<Movie> movies;
    private Context context;

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_detail, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder moviesViewHolder, int i) {
        moviesViewHolder.textView.setText(movies.get(i).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w300"+movies.get(i).getImg_url()).into(moviesViewHolder.imageView);
        moviesViewHolder.release_date.setText(movies.get(i).getRelease_date().substring(0,4)+"");
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView release_date;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView = itemView.findViewById(R.id.movie_poster);
           textView = itemView.findViewById(R.id.movie_title);
           release_date = itemView.findViewById(R.id.release_year);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Gson gson = new Gson();
                   Movie selected_movie = movies.get(getAdapterPosition()) ;
                   Log.d("selcted for detail",movies.get(getAdapterPosition()).getId()+"");
                   Intent intent = new Intent(v.getContext(),DetailedInfo.class);
                   intent.putExtra("detailed",movies.get(getAdapterPosition()).getId());
                   v.getContext().startActivity(intent);
               }
           });
        }
    }
}

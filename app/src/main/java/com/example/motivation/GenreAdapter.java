package com.example.motivation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private List<String> all_genres;

    public GenreAdapter(List<String> all_genres) {
        this.all_genres = all_genres;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.genre_item, viewGroup, false);
        return new GenreAdapter.GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder genreViewHolder, int i) {
            genreViewHolder.genre_name.setText(all_genres.get(i));
    }

    @Override
    public int getItemCount() {
       if(all_genres==null) return 0;
       else return all_genres.size();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genre_name;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genre_name = itemView.findViewById(R.id.genre_name);
        }
    }
}

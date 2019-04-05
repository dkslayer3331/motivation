package com.example.motivation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchedActivity extends AppCompatActivity {

    RecyclerView search_rv;
    SearchAdapter searchAdapter;
    ArrayList<Movie> all_search_movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);
        search_rv = findViewById(R.id.search_movie_rv);
        searchAdapter = new SearchAdapter(all_search_movies,this);
        search_rv.setLayoutManager(new GridLayoutManager(this,3));
        search_rv.setAdapter(searchAdapter);

        Intent intent = getIntent();
        Log.d("searched movie",intent.getStringExtra("name"));

        MovieAPI service = API.getInstance().getMovieAPI();
        service.getSearchedMovies("a7fc563ba6989aec1e19d62d2d1985c9",intent.getStringExtra("name")).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d("search_result",new Gson().toJson(response.body().getResults()));
                searchAdapter = new SearchAdapter(response.body().getResults(),getApplicationContext());
                search_rv.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }


}

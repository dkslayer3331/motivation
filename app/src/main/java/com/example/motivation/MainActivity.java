package com.example.motivation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView upcoming_movies_recyclerview;
    MoviesAdapter moviesAdapter;
    ArrayList<Movie> retro_results = new ArrayList<>();
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = findViewById(R.id.test_button);

        MovieAPI service = API.getInstance().getMovieAPI();

        service.getUpcomingMovies("a7fc563ba6989aec1e19d62d2d1985c9").enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d("result",response.body().getTotalPages()+"");
                retro_results = response.body().getResults();
                moviesAdapter = new MoviesAdapter(getApplicationContext(),retro_results);
                upcoming_movies_recyclerview.setAdapter(moviesAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("gege",t.getMessage());
            }
        });

        upcoming_movies_recyclerview = findViewById(R.id.upcoming_movies_recyclerview);
        moviesAdapter = new MoviesAdapter(getApplicationContext(),retro_results);
        upcoming_movies_recyclerview.setAdapter(moviesAdapter);

       test.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(v.getContext(),FavMovies.class);
               startActivity(intent);
           }
       });


    }
}

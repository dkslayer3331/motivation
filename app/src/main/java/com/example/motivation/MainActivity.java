package com.example.motivation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView upcoming_movies_recyclerview;
    RecyclerView popular_movies_recyclerview;
    MoviesAdapter moviesAdapter;//for upcoming movies
    MoviesAdapter pAdapter; // for popular movies
    ArrayList<Movie> retro_results = new ArrayList<>(); // upcoming
    ArrayList<Movie> popular_reults = new ArrayList<>();
    Button test;
    android.support.v7.widget.SearchView searchView;
    ProgressBar progressBar;
    LinearLayout main_layout;


    int show_flag = 0;

    void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.GONE);
    }

    void hideProgressBar(){
        if(show_flag == 2){
            progressBar.setVisibility(View.GONE);
            main_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.movie_search);

        searchView =(android.support.v7.widget.SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(listener);

        return true;
    }

   public android.support.v7.widget.SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           Intent intent = new Intent(getApplicationContext(),SearchedActivity.class);
           intent.putExtra("name",s);
           startActivity(intent);
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           return false;
       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = findViewById(R.id.test_button);
        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progress_bar);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        showProgressBar();

        popular_movies_recyclerview = findViewById(R.id.popular_movies_recyclerview);
        pAdapter = new MoviesAdapter(getApplicationContext(),popular_reults);
        popular_movies_recyclerview.setAdapter(pAdapter);

        MovieAPI service = API.getInstance().getMovieAPI();

        service.getUpcomingMovies("a7fc563ba6989aec1e19d62d2d1985c9").enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d("result",response.body().getTotalPages()+"");
                retro_results = response.body().getResults();
                moviesAdapter = new MoviesAdapter(getApplicationContext(),retro_results);
                upcoming_movies_recyclerview.setAdapter(moviesAdapter);
                show_flag++;
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("gege",t.getMessage());
            }

        });

        service.getPopularMovies("a7fc563ba6989aec1e19d62d2d1985c9").enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                popular_reults = response.body().getResults();
                pAdapter = new MoviesAdapter(getApplicationContext(),popular_reults);
                popular_movies_recyclerview.setAdapter(pAdapter);
                show_flag++;
                hideProgressBar();
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

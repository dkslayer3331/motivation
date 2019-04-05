package com.example.motivation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CastDetail extends AppCompatActivity {

    ImageView cast_profile;
    RecyclerView known_for_movies;
    MoviesAdapter moviesAdapter;
    TextView cast_detail_title,birth_date,cast_roles,biography;

    ArrayList<Movie> known_movies = new ArrayList<>();

//    public String getAllGenres(List<String> names)
//    {
//        StringBuilder namesStr = new StringBuilder();
//        for(String name : names)
//        {
//            namesStr = namesStr.length() > 0 ? namesStr.append(",").append(name) : namesStr.append(name);
//        }
//        return namesStr.toString();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_detail);

        cast_profile = findViewById(R.id.cast_detail_profile);
        known_for_movies = findViewById(R.id.cast_detail_knownmovies);
        cast_detail_title = findViewById(R.id.cast_detail_title);
        birth_date = findViewById(R.id.born_date);
        cast_roles = findViewById(R.id.cast_detail_roles);
        biography = findViewById(R.id.cast_detail_biography);

        moviesAdapter = new MoviesAdapter(this,known_movies);
        known_for_movies.setAdapter(moviesAdapter);

        Intent intent = getIntent();
        long cast_detail_id = intent.getLongExtra("cast_id",0);

        Log.d("heyy cast id",cast_detail_id+"");

        MovieAPI service = API.getInstance().getMovieAPI();

        service.getCastDetail(cast_detail_id,"a7fc563ba6989aec1e19d62d2d1985c9","movie_credits").enqueue(new Callback<Cast>() {
            @Override
            public void onResponse(Call<Cast> call, Response<Cast> response) {

                known_movies = response.body().getMovieCredits().getKnown_movies();

                moviesAdapter = new MoviesAdapter(getApplicationContext(),known_movies);

                known_for_movies.setAdapter(moviesAdapter);

                Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185"+response.body().getProfile_url()).into(cast_profile);

                cast_detail_title.setText(response.body().getName());

                biography.setText(response.body().getBiography());

                cast_roles.setText(response.body().getKnown_for_department());

            }

            @Override
            public void onFailure(Call<Cast> call, Throwable t) {
                Log.d("err get cast detail",t.getMessage());
            }
        });


    }
}

package com.example.motivation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String base_url = "https://api.themoviedb.org/3/";

    private static API instance = null;

    private MovieAPI movieAPI;

    public static API getInstance() {
        if(instance == null) instance = new API();
        return instance;
    }

    public API() {
        buildRetrofit();
    }

    private void buildRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.movieAPI = retrofit.create(MovieAPI.class);
    }

    public MovieAPI getMovieAPI() {
        return movieAPI;
    }
}

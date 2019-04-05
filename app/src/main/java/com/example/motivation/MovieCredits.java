package com.example.motivation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieCredits {
//    @SerializedName("cast")
//    private ArrayList<Cast> casts;
//
//    @SerializedName("crew")
//    private ArrayList<Crew> crews;
//
//    public ArrayList<Cast> getCasts() {
//        return casts;
//    }
//
//    public ArrayList<Crew> getCrews() {
//        return crews;
//    }
    @SerializedName("cast")
    private ArrayList<Movie> known_movies;

    public ArrayList<Movie> getKnown_movies() {
        return known_movies;
    }
}

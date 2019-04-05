package com.example.motivation;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("character")
    private String character;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("known_for_department")
    private String known_for_department;

    @SerializedName("deathday")
    @Nullable private String deathday;

    @SerializedName("biography")
    private String biography;

    @SerializedName("movie_credits")
    private MovieCredits movieCredits;

    public String getBirthday() {
        return birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    @Nullable
    public String getDeathday() {
        return deathday;
    }

    public String getBiography() {
        return biography;
    }

    public MovieCredits getMovieCredits() {
        return movieCredits;
    }

    @SerializedName("cast_id")
    private int cast_id;

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profile_url;

    public String getCharacter() {
        return character;
    }

    public int getCast_id() {
        return cast_id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_url() {
        return profile_url;
    }
}

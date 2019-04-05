package com.example.motivation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Credits {
    @SerializedName("cast")
    private ArrayList<Cast> casts;

    @SerializedName("crew")
    private ArrayList<Crew> crews;

    public ArrayList<Crew> getCrews() {
        return crews;
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }
}

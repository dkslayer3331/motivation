package com.example.motivation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Movie> results;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}

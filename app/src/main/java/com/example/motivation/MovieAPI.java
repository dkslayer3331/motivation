package com.example.motivation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetail(@Path("id") long movie_id,@Query("api_key") String apiKey,@Query("append_to_response")String word);

    @GET("person/{id}")
    Call<Cast> getCastDetail(@Path("id") long cast_id,@Query("api_key") String apiKey,@Query("append_to_response")String movie_credits);

    @GET("search/movie")
    Call<MovieResponse> getSearchedMovies(@Query("api_key") String apiKey,@Query("query")String query);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

}

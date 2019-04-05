package com.example.motivation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavMoviesDao {

    @Query("select * from fav_movies")
    List<FavMovieObj> getAllFavMovies();

   @Insert
    void insert(FavMovieObj movie);

   @Query("delete from fav_movies")
    void deleteAll();
}

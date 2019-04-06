package com.example.motivation;

import android.app.Application;
import android.content.Context;

import java.util.List;

public class Repository {
    private FavMoviesDao favMoviesDao;
    private List<FavMovieObj> favMovieObjList;

    public Repository(Application application) {
        FavMoviesDb db = FavMoviesDb.getDatabase(application);
        favMoviesDao = db.favMoviesDao();
        favMovieObjList = favMoviesDao.getAllFavMovies();
    }

        List<FavMovieObj> getAllFavMovies(){
        return favMovieObjList;
        }
}

package com.example.motivation;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavMovieObj.class},version = 2,exportSchema = false)
public abstract class FavMoviesDb extends RoomDatabase {

    public abstract FavMoviesDao favMoviesDao();

    public static volatile FavMoviesDb instance = null;

    static FavMoviesDb getDatabase(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavMoviesDb.class, "fav_movies_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}

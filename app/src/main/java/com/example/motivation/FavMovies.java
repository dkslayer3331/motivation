package com.example.motivation;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavMovies extends AppCompatActivity {

    RecyclerView recyclerView;
    FavMoviesAdapter favMoviesAdapter;
    List<FavMovieObj> all_fav_movies = new ArrayList<>();
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movies);
        recyclerView = findViewById(R.id.fav_movies_rv);
        floatingActionButton = findViewById(R.id.clear_fav_movies);

        Log.d("before_asyn",all_fav_movies.size()+"");

        favMoviesAdapter = new FavMoviesAdapter(this,all_fav_movies);
        recyclerView.setAdapter(favMoviesAdapter);

        FavMoviesDb db = FavMoviesDb.getDatabase(this);
        final FavMoviesDao dao = db.favMoviesDao();
        new getAllFavAsyn(dao).execute();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavMovies.this);
                builder.setMessage("Wanna clear list of fav movies?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<FavMovieObj> clear_data = new ArrayList<>();
                                new deleteAllAsync(dao).execute();
                                favMoviesAdapter = new FavMoviesAdapter(getApplicationContext(),clear_data);
                                recyclerView.setAdapter(favMoviesAdapter);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

     //   Log.d("after_asyn",all_fav_movies.size()+"");
    }

    class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private FavMoviesDao favMoviesDao;

        public deleteAllAsync(FavMoviesDao favMoviesDao) {
            this.favMoviesDao = favMoviesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favMoviesDao.deleteAll();
            return null;
        }
    }

    class getAllFavAsyn extends AsyncTask<Void,Void,Void>{
        private FavMoviesDao dao;

        public getAllFavAsyn(FavMoviesDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favMoviesAdapter = new FavMoviesAdapter(getApplicationContext(),dao.getAllFavMovies());
            Log.d("fav_movie_size",dao.getAllFavMovies().size()+"");
            recyclerView.setAdapter(favMoviesAdapter);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

}

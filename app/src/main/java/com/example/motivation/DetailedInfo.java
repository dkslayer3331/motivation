package com.example.motivation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedInfo extends AppCompatActivity {

    TextView release_year,duration,genre,plot;
    ImageView poster;
    RecyclerView casts_rv;
    CastAdapter castAdapter;
    TextView director;
    TextView writers;
    TextView movie_header;
    ToggleButton add_favs;
    ProgressBar detail_progressbar;
    LinearLayout detail_info_layout;
    boolean fav_button_status = false;
    boolean show_snack_bar_add = true;

  List<String> all_genres = new ArrayList<>();
  ArrayList<Cast> all_casts = new ArrayList<>();

    Movie detailed_movie = new Movie();

    public static String getFormattedList(List<String> names)
    {
        StringBuilder namesStr = new StringBuilder();
        for(String name : names)
        {
            namesStr = namesStr.length() > 0 ? namesStr.append(", ").append(name) : namesStr.append(name);
        }
        return namesStr.toString();
    }

//    public boolean checkDuplicate(List<FavMovieObj> all_favs,Movie movie){
//        for (FavMovieObj favMovieObj:all_favs) {
//            if(favMovieObj.getMovie_id()== movie.getId()) return false;
//        }
//        return  true;
//    }

   void showDetailProgress(){
        detail_progressbar.setVisibility(View.VISIBLE);
        detail_info_layout.setVisibility(View.GONE);
   }

   void showDetailView(){
        detail_info_layout.setVisibility(View.VISIBLE);
        detail_progressbar.setVisibility(View.GONE);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final long detailed = intent.getLongExtra("detailed",0);

        detail_info_layout = findViewById(R.id.detail_info_layout);
        detail_progressbar = findViewById(R.id.detail_progress_bar);
        Sprite doubleBounce = new DoubleBounce();
        detail_progressbar.setIndeterminateDrawable(doubleBounce);

        showDetailProgress();

        movie_header = findViewById(R.id.movie_title_header);
        add_favs = findViewById(R.id.add_to_favs);
        writers = findViewById(R.id.detail_writers);
        director = findViewById(R.id.detail_director);
        casts_rv = findViewById(R.id.casts_rv);
        release_year = findViewById(R.id.detail_year);
        duration = findViewById(R.id.detail_duration);
        genre = findViewById(R.id.detail_genre);
        poster = findViewById(R.id.detailed_movie_poster);
        plot = findViewById(R.id.detail_movie_plot);

       //casts blank arraylist
        castAdapter = new CastAdapter(this,all_casts);
        casts_rv.setAdapter(castAdapter);

        MovieAPI service = API.getInstance().getMovieAPI();

        service.getMovieDetail(detailed,"a7fc563ba6989aec1e19d62d2d1985c9","credits").enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                ArrayList<Genre> genre_obj = response.body().getGenres();

                detailed_movie = response.body();

                ArrayList<Crew> crews = response.body().getCredits().getCrews();

                FavMoviesDb db = FavMoviesDb.getDatabase(getApplicationContext());
                FavMoviesDao dao = db.favMoviesDao();

                List<FavMovieObj> all_favs = new Repository(getApplication()).getAllFavMovies();

                for (FavMovieObj favMovieObj:all_favs) {
                    if(favMovieObj.getMovie_id()== detailed){
                        show_snack_bar_add = false;
                        add_favs.setChecked(true);
                    }
                }

                Log.d("all crews",new Gson().toJson(crews));

                List<String> directors = new ArrayList<>();

                release_year.setText(response.body().getRelease_date().substring(0,4));

                plot.setText(response.body().getOverview());

                Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w154"+response.body().getImg_url()).into(poster);

                movie_header.setText(response.body().getTitle());

                setTitle(response.body().getTitle());

                for(Crew crew:crews){
                    if(crew.getJob().equals("Director")){
                        director.setText(crew.getName());
                        Log.d("director",crew.getName());
                    }
                    if(crew.getDepartment().equals("Writing")) {
                        directors.add(crew.getName());
                        Log.d("writer",crew.getJob());
                    }
                }

                Log.d("all writers",getFormattedList(directors));

                writers.setText(getFormattedList(directors));

                    for(Genre genre:genre_obj){
                        all_genres.add(genre.getName());
                    }

                genre.setText(getFormattedList(all_genres));

                long movie_runtime_min = response.body().getRuntime()%60;

                long movie_runtime_hour = response.body().getRuntime()/60;

               if(movie_runtime_min >0) duration.setText(movie_runtime_hour+"hr "+movie_runtime_min+"min");

               else duration.setText(movie_runtime_hour+"hr");


                String log_obj = new Gson().toJson(response.body().getCredits().getCasts());

                Log.d("all casts",log_obj);

                castAdapter = new CastAdapter(getApplicationContext(),response.body().getCredits().getCasts());

                casts_rv.setAdapter(castAdapter);

                showDetailView();

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                    Log.d("cancer err",t.getMessage());
            }
        });

        add_favs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FavMoviesDb db = FavMoviesDb.getDatabase(DetailedInfo.this);
                FavMoviesDao dao = db.favMoviesDao();
                if(isChecked){
                    new insertAsyntask(dao).execute(detailed_movie);
                   if(show_snack_bar_add)  Snackbar.make(buttonView,"Added to favourite movies",Snackbar.LENGTH_LONG).setAction("See List",new FavListListener()).show();
                }
                else{
                    show_snack_bar_add = true;
                    new removeAsyntask(dao).execute(detailed);
                    Toast.makeText(DetailedInfo.this, "Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        add_favs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FavMoviesDb db = FavMoviesDb.getDatabase(v.getContext());
//                FavMoviesDao dao = db.favMoviesDao();
//
//                List<FavMovieObj> current_list = new Repository(getApplication()).getAllFavMovies();
//
//              if(checkDuplicate(current_list,detailed_movie)) {
//                  fav_button_status = true;
//              }
//
//                else fav_button_status = false;
//
//                if(fav_button_status){
//                     Log.d("YMT","yes");
//                     new insertAsyntask(dao).execute(detailed_movie);
//                     Snackbar.make(v,"Added to favourite movies",Snackbar.LENGTH_LONG).setAction("See List",new FavListListener()).show();
//                }
//
//                else {
//                    Log.d("YMT","no");
//                    Snackbar.make(v,"Already Added",Snackbar.LENGTH_LONG).show();
//                }
//
//            }
//
//        });

    }

    class removeAsyntask extends AsyncTask<Long,Void,Void>{
        private FavMoviesDao favMoviesDao;

        public removeAsyntask(FavMoviesDao favMoviesDao) {
            this.favMoviesDao = favMoviesDao;
        }

        @Override
        protected Void doInBackground(Long... longs) {
            favMoviesDao.removeMovie(longs[0]);
            return null;
        }
    }


    class insertAsyntask extends AsyncTask<Movie,Void,Void>{

        private FavMoviesDao favMoviesDao;

        public insertAsyntask(FavMoviesDao favMoviesDao) {
            this.favMoviesDao = favMoviesDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            List<String> all_genres = new ArrayList<>();

            for (Genre genre: movies[0].getGenres()) {
                all_genres.add(genre.getName());
            }

            FavMovieObj fav = new FavMovieObj();
            fav.setTitle(movies[0].getTitle());
            fav.setImg_url(movies[0].getImg_url());
            fav.setGenre(getFormattedList(all_genres));
            fav.setMovie_id(movies[0].getId());

            favMoviesDao.insert(fav);

            Log.d("inserted movie",new Gson().toJson(fav));
            return null;
        }

    }

}

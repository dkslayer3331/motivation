package com.example.motivation;

import android.content.Intent;
import android.view.View;


public class FavListListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), FavMovies.class);
       v.getContext().startActivity(intent);
    }
}

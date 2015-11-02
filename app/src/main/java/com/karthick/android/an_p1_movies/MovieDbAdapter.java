package com.karthick.android.an_p1_movies;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by karthick.jenadoss on 11/1/2015.
 */


    public class MovieDbAdapter extends ArrayAdapter<MovieDb> {
        private static final String LOG_TAG = MovieDbAdapter.class.getSimpleName();
    final String MOVIEIMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

        public MovieDbAdapter(Activity context, List<MovieDb> Movies)
        {
            super(context, 0, Movies);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MovieDb movie = getItem(position);


            // Adapters recycle views to AdapterViews.
            // If this is a new View object we're getting, then inflate the layout.
            // If not, this view already has the layout inflated from a previous call to getView,
            // and we modify the View widgets as usual.
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_flavor, parent, false);
            }

            ImageView iconView = (ImageView) convertView.findViewById(R.id.list_item_icon);
            //iconView.setImageResource(movie.image);


            try{
                Uri builtUri = Uri.parse(MOVIEIMAGE_BASE_URL).buildUpon()
                        .appendPath(movie.getPosterPath())
                        .build();
                iconView.setImageURI(builtUri);

            }catch (Exception e){
                Log.e(LOG_TAG, e.getMessage());
            }

            /*
            TextView versionNameView = (TextView) convertView.findViewById(R.id.list_item_version_name);
            versionNameView.setText(movie.getTitle());

            TextView versionNumberView = (TextView) convertView.findViewById(R.id.list_item_versionnumber_textview);
            Calendar movereleasedatecal = new GregorianCalendar();

            try{

                Date moviereleasedate = new SimpleDateFormat("MM/DD/YYYY", Locale.ENGLISH).parse(movie.getReleaseDate());
                movereleasedatecal.setTime(moviereleasedate);
                versionNumberView.setText(movereleasedatecal.get(Calendar.YEAR));
            }catch (Exception e){
                Log.e(LOG_TAG, e.getMessage());
            }
                */
            return convertView;
        }
}

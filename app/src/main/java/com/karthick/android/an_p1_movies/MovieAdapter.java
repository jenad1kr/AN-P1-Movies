package com.karthick.android.an_p1_movies;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by karthick.jenadoss on 11/1/2015.
 */


    public class MovieAdapter extends ArrayAdapter<Movie> {
        private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    final String MOVIEIMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

        public MovieAdapter(Activity context, List<Movie> Movies)
        {
            super(context, 0, Movies);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Movie movie = getItem(position);


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
                        .appendEncodedPath(movie.posterPath)
                        .build();
                //Log.i(LOG_TAG, builtUri.toString());
                //iconView.setImageURI(builtUri);
                //iconView.setImageURI(Uri.parse("http://image.tmdb.org/t/p/w185//q0R4crx2SehcEEQEkYObktdeFy.jpg"));
                Picasso.with(getContext()).load(builtUri).into(iconView);

            }catch (Exception e){
                Log.e(LOG_TAG, e.getMessage());
            }


            TextView movieNameView = (TextView) convertView.findViewById(R.id.list_item_movie_name);
            movieNameView.setText(movie.movieName);

            TextView movieRatingView = (TextView) convertView.findViewById(R.id.list_item_movie_rating);
            movieRatingView.setText(String.valueOf(movie.movieRating));

            TextView releaseDateView = (TextView) convertView.findViewById(R.id.list_item_movie_release_date);
            Calendar movereleasedatecal = new GregorianCalendar();
            try{
                //Log.i(LOG_TAG, movie.movieReleaseDate);
                Date moviereleasedate = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(movie.movieReleaseDate);
                movereleasedatecal.setTime(moviereleasedate);
                //Log.i(LOG_TAG, String.valueOf(movereleasedatecal.get(Calendar.YEAR)));
                releaseDateView.setText(String.valueOf(movereleasedatecal.get(Calendar.YEAR)));
                //releaseDateView.setText("dummy date");
            }catch (Exception e){
                Log.e(LOG_TAG, e.getMessage());
            }

            return convertView;
        }
}

package com.karthick.android.an_p1_movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    final String MOVIEIMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    public DetailActivityFragment() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.getActivity().finish();
                Log.i("onoptitemsel", "this.finish called starting mainactivity");
                startActivity(new Intent(this.getActivity(), MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        if (intent != null) {
            Movie movie = intent.getParcelableExtra("selected_movie");
            //Log.i("moviename", movie.movieName);
            ((TextView) rootView.findViewById(R.id.list_item_movie_name)).setText(movie.movieName);
            ((TextView) rootView.findViewById(R.id.list_item_movie_release_date)).setText(movie.movieReleaseDate);
            String rating = String.valueOf(movie.movieRating);
            //Log.i("rating", rating);
            ((TextView) rootView.findViewById(R.id.list_item_movie_rating)).setText(rating);
            //((TextView) rootView.findViewById(R.id.plot_synopsis)).setText(movie.plotSynopsis);
            ((TextView) rootView.findViewById(R.id.plot_synopsis)).setText(Html.fromHtml(movie.plotSynopsis));
            ((TextView) rootView.findViewById(R.id.plot_synopsis)).setMovementMethod(new ScrollingMovementMethod());
            //Log.i("synopsis", movie.plotSynopsis);
            ImageView iconView = (ImageView) rootView.findViewById(R.id.list_item_icon);

            try {
                Uri builtUri = Uri.parse(MOVIEIMAGE_BASE_URL).buildUpon()
                        .appendEncodedPath(movie.posterPath)
                        .build();
                //Picasso.with(getContext()).load(builtUri).into(iconView);
                Picasso.with(getContext())
                        .load(builtUri)
                        .placeholder(R.drawable.cupcake)
                        .error(R.drawable.honeycomb)
                        .into(iconView);


            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return rootView;
    }


}

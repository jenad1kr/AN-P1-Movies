package com.karthick.android.an_p1_movies;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private AndroidFlavorAdapter flavorAdapter;
    private MovieDbAdapter movieDbAdapter;
    private MovieAdapter movieAdapter;



    AndroidFlavor[] androidFlavors = {
            new AndroidFlavor("Cupcake", "1.5", R.drawable.cupcake),
            new AndroidFlavor("Donut", "1.6", R.drawable.donut),
            new AndroidFlavor("Eclair", "2.0-2.1", R.drawable.eclair),
            new AndroidFlavor("Froyo", "2.2-2.2.3", R.drawable.froyo),
            new AndroidFlavor("GingerBread", "2.3-2.3.7", R.drawable.gingerbread),
            new AndroidFlavor("Honeycomb", "3.0-3.2.6", R.drawable.honeycomb),
            new AndroidFlavor("Ice Cream Sandwich", "4.0-4.0.4", R.drawable.icecream),
            new AndroidFlavor("Jelly Bean", "4.1-4.3.1", R.drawable.jellybean),
            new AndroidFlavor("KitKat", "4.4-4.4.4", R.drawable.kitkat),
            new AndroidFlavor("Lollipop", "5.0-5.1.1", R.drawable.lollipop)
    };

    ArrayList<AndroidFlavor> androidFlavorArrayList;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String SORTBY_PARAM = "sort_by";
        final String FORMAT_PARAM = "mode";
        final String UNITS_PARAM = "units";
        final String DAYS_PARAM = "cnt";
        final String APIKEY_PARAM = "api_key";


        //return inflater.inflate(R.layout.fragment_main, container, false);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        //ListView listView = (ListView) rootView.findViewById(R.id.listview_flavor);
        //listView.setAdapter(flavorAdapter);

        //TmdbMovies movies = new TmdbApi("API_KEY").getMovies();
        //MovieDb movie = movies.getMovie(5353, "en");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String MoviesJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast


/*
            Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                                    .appendQueryParameter(QUERY_PARAM, params[0])
                                    .appendQueryParameter(FORMAT_PARAM, format)
                                    .appendQueryParameter(UNITS_PARAM, units)
                                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                                    .appendQueryParameter(APPKEY_PARAM, R.string.moviedb_apikey)
                                    .build();

                                    */

            String sortBy = "popularity.desc";
            Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                    .appendQueryParameter(SORTBY_PARAM, sortBy)
                    .appendQueryParameter(APIKEY_PARAM, "88695003bd93ff97de5678f06d58fe4a")
                    .build();


            URL url = new URL(builtUri.toString());
            Log.v("LOGMSG", "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();


            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here



            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            }
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            MoviesJsonStr = buffer.toString();
            Log.v("LOGMSG", "returned JSON STR " + MoviesJsonStr);
            movieAdapter = new MovieAdapter(getActivity(),MovieJSONParser.getMovies(MoviesJsonStr));
            GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
            gridView.setAdapter(movieAdapter);



        } catch (Exception e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        //List<MovieDb> moviedblist = movies.getPopularMovieList("en", 1).getResults();



        //flavorAdapter = new AndroidFlavorAdapter(getActivity(), Arrays.asList(androidFlavors));
        //flavorAdapter = new AndroidFlavorAdapter(getActivity(), androidFlavorArrayList);

        //use the following if using Movie Object


        //use the following for MovieDb
        //GridView gridView = (GridView) rootView.findViewById(R.id.flavors_grid);
        //gridView.setAdapter(movieDbAdapter);




        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("flavors", androidFlavorArrayList  );
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("flavors")) {
            androidFlavorArrayList = new ArrayList<>(Arrays.asList(androidFlavors));
        }
        else{
            androidFlavorArrayList = savedInstanceState.getParcelableArrayList("flavors");
        }
    }
}

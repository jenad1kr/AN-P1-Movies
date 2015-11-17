package com.karthick.android.an_p1_movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
    private MovieAdapter movieAdapter;
    private FetchMoviesTask fetchMoviesTask;


    public MainActivityFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        movieAdapter = new MovieAdapter(getActivity(),UpdateMoviesList());
        Log.i("OnCreateView","UpdateMoviesList called");
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getContext();
                CharSequence text = ((Movie) adapterView.getItemAtPosition(i)).movieName;
                int duration = Toast.LENGTH_SHORT;Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent DetailIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("selected_movie", (Movie) adapterView.getItemAtPosition(i));
                startActivity(DetailIntent);
            }
        });

        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("flavors", androidFlavorArrayList  );
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //UpdateMoviesList();
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

    private ArrayList<Movie> UpdateMoviesList() {
        fetchMoviesTask = new FetchMoviesTask();

        try {
            Log.i("updmovieslist","UpdateMoviesList called");
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        /*
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(getString(R.string.pref_popular_key),"desc");
        edit.putString(getString(R.string.pref_rating_key),"desc");
        edit.commit();
        */
            String[] params = new String[1];
            params[0] = prefs.getString("sort_order", "popularity.desc");
            Log.i("params[0]", params[0]);

            return fetchMoviesTask.execute(params).get();
        }catch (Exception e){
            Log.e("updatemovieslist", e.getMessage());
        }
        return null;
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();


        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String MoviesJsonStr;
            //String[] returnvalue = new String[1];

            try {


                Uri builtUri = Uri.parse(getString(R.string.url_moiveDB)).buildUpon()
                        .appendQueryParameter(getString(R.string.url_param_sort_by), params[0])
                        .appendQueryParameter(getString(R.string.url_param_api_key), getString(R.string.moviedb_apikey))
                        .build();


                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
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
                Log.v(LOG_TAG, "returned JSON STR " + MoviesJsonStr);
                //returnvalue[0] = MoviesJsonStr;
                //return returnvalue;

                return MovieJSONParser.getMovies(MoviesJsonStr);
            } catch (Exception e) {
                Log.e("PlaceholderFragment", "Error ", e);
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
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
        }


    }
}



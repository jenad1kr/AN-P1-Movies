package com.karthick.android.an_p1_movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by karthick.jenadoss on 11/1/2015.
 */
public class MovieJSONParser {
    public static Movie getMovieObject(String weatherJsonStr, int dayIndex)
            throws JSONException {

        JSONObject movies = new JSONObject(weatherJsonStr);
        JSONObject movie = movies.getJSONArray("results").getJSONObject(dayIndex);
        return new Movie(movie.getString("title"),
                            movie.getDouble("vote_average"),
                            movie.getString("release_date"),
                            movie.getString("poster_path")
                              );
        //return Double.parseDouble(maxval);


    }

    public static ArrayList<Movie> getMovies(String moviesJsonStr )
            throws JSONException {

        JSONObject outerObj = new JSONObject(moviesJsonStr);
        JSONArray movies = outerObj.getJSONArray("results");
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        for(int i = 0 ; i < movies.length(); i++){
            //JSONObject obj   = movies.getJSONObject(i);
            movieArrayList.add(new Movie(movies.getJSONObject(i).getString("title"),
                    movies.getJSONObject(i).getDouble("vote_average"),
                    movies.getJSONObject(i).getString("release_date"),
                    movies.getJSONObject(i).getString("poster_path")
            ));
        }

        return movieArrayList;


    }

}

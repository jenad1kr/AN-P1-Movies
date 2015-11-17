package com.karthick.android.an_p1_movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karthick.jenadoss on 11/1/2015.
 */
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    String movieName;
    Double movieRating;
    String movieReleaseDate;
    String posterPath;
    String plotSynopsis;

    public Movie(String vName,
                 Double vRating,
                 String vmovieReleaseDate,
                 String vposterPath,
                 String vplotSynopsis)
    {
        this.movieName = vName;
        this.movieRating = vRating;
        this.movieReleaseDate = vmovieReleaseDate;
        this.posterPath = vposterPath;
        this.plotSynopsis = vplotSynopsis;
    }
    public Movie(Parcel in){
        movieName = in.readString();
        movieRating = in.readDouble();
        movieReleaseDate = in.readString();
        posterPath = in.readString();
        plotSynopsis = in.readString();
    }

    //int image; // drawable reference id

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return movieName + "--" + movieRating + "--" + movieReleaseDate + "--" + posterPath + "--" + plotSynopsis;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeDouble(movieRating);
        dest.writeString(movieReleaseDate);
        dest.writeString(posterPath);
        dest.writeString(plotSynopsis);

    }

}

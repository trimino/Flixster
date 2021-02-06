package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {


    String posterPath;
    String title;
    String overview;
    String backPath;
    String relDate;
    int movieID;
    double rating;


    // Empty constructor for Parceler Library
    public Movie() {}

    public Movie(JSONObject jsonObject)throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.title      = jsonObject.getString("title");
        this.overview   = jsonObject.getString("overview");
        this.backPath   = jsonObject.getString("backdrop_path");
        this.relDate    = jsonObject.getString("release_date");
        this.movieID    = jsonObject.getInt("id");
        this.rating     = jsonObject.getDouble("vote_average");
    }


    public static List<Movie> fromJsonArray(JSONArray jsonArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            movies.add(
                    new Movie(jsonArray.getJSONObject(i))
            );
        }
        return movies;
    }

    // TODO: Make an API request to Configuration API
    // TODO: Figure out what sizes of images are available
    // TODO: Appending the size to the base URL (configuration URL)
    // TODO: Appending the relative path from posterPath
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackPath(){ return String.format("https://image.tmdb.org/t/p/w342/%s", backPath); }

    public double getRating()  { return rating; }

    public String getRelDate() { return relDate; }

    public int getMovieID() { return movieID; }
}

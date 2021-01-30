package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String posterPath;
    String title;
    String overview;
    String backPath;

    public Movie(JSONObject jsonObject)throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.title      = jsonObject.getString("title");
        this.overview   = jsonObject.getString("overview");
        this.backPath   = jsonObject.getString("backdrop_path");
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
}

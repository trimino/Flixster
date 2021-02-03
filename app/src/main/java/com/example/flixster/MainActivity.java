package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY              = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String NOW_PLAYING_URL      = String.format("https://api.themoviedb.org/3/movie/now_playing?api_key=%s", API_KEY);

    public static final String TAG                  = "MainActivity";

    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();

        /* ----------------------> CODE FOR RECYCLE VIEW <---------------------- */
        // Create adapter
        MovieAdapter movieAdapter = new MovieAdapter(movies, this);

        // RecycleView
        RecyclerView rvMovies = findViewById(R.id.rvMovie);

        // set the adapter on the recycle view
        rvMovies.setAdapter(movieAdapter);

        // set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        DividerItemDecoration dividerItemDecorationVertical = new DividerItemDecoration(this.getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecorationVertical.setDrawable(this.getApplicationContext().getResources().getDrawable(R.drawable.divider_line));

        DividerItemDecoration dividerItemDecorationHorizontal = new DividerItemDecoration(this.getApplicationContext(),
                DividerItemDecoration.HORIZONTAL);

        rvMovies.addItemDecoration(dividerItemDecorationVertical);
        rvMovies.addItemDecoration(dividerItemDecorationHorizontal);

        /* ----------------------> GET NOW PLAYING MOVIES <---------------------- */
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "--> onSuccess <--");
                Log.i(TAG, String.format("API_URL: %s", NOW_PLAYING_URL));

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, String.format("Results: %s", results.toString()));

                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, String.format("Movies: %s", movies.size()));

                }catch (JSONException e){
                    Log.e(TAG, "JSON EXCEPTION in client variable");
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "--> onFailure in client variable <--");
            }
        });
        // ----------------------> END OF GETTING NOW PLAYING MOVIES <----------------------
    }
}
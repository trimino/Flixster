package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private final String YOUTUBE_API_KEY    = "YOUR_API_KEY";
    private final String VIDEO_URL          = "https://api.themoviedb.org/3/movie/%d/videos?api_key=MOVIE_DB_API_KEY";

    TextView tvTitle;
    TextView tvOverview;
    TextView tvReleaseDate;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie         = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle             = findViewById(R.id.tvTitle);
        tvOverview          = findViewById(R.id.tvOverview);
        tvReleaseDate       = findViewById(R.id.tvReleaseDate);
        ratingBar           = findViewById(R.id.ratingBar);
        youTubePlayerView   = (YouTubePlayerView) findViewById(R.id.player);


        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(String.format("Release Date: %s", movie.getRelDate()));
        ratingBar.setRating((float) movie.getRating());


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0)
                        return;
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", String.format("--> %s", youtubeKey));
                    initializeYouTube(youtubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("DetailActivity", "--> Failed to parse JSON in DetailActivity");
            }
        });
    }

    private void initializeYouTube(final String youtubeKey){
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "--> onInitializationSuccess");
                youTubePlayer.loadVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "--> onInitializationFailure");
                Log.d("DetailActivity", String.format("--> YOUTUBE_API_KEY: %s", YOUTUBE_API_KEY));
            }
        });
    }
}

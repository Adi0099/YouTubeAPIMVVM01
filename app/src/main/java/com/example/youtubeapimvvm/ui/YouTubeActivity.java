package com.example.youtubeapimvvm.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtubeapimvvm.R;
import com.example.youtubeapimvvm.utils.Constants;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    TextView name_tv,desc_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        youTubePlayerView=findViewById(R.id.youtubeplayer_view);
        name_tv=findViewById(R.id.title);
        desc_tv=findViewById(R.id.description);

        final String videoid=getIntent().getStringExtra("videoID");
        final String name=getIntent().getStringExtra("name");
        final String description=getIntent().getStringExtra("desc");

        name_tv.setText(name);
        desc_tv.setText(description);

        youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoid);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(YouTubeActivity.this, 1).show();
                } else {
                    String error = String.format("Error in Youtube Player", youTubeInitializationResult.toString());
                    Toast.makeText(YouTubeActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}

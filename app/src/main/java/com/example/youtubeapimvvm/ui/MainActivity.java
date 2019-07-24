package com.example.youtubeapimvvm.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.youtubeapimvvm.R;
import com.example.youtubeapimvvm.models.YoutubeResponse;
import com.example.youtubeapimvvm.utils.AlertMessage;
import com.example.youtubeapimvvm.utils.MyApplication;
import com.example.youtubeapimvvm.view.adapter.VideosAdapter;
import com.example.youtubeapimvvm.viewmodel.VideoListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private VideosAdapter adapter;

    private String lastToken = "";
    CountDownTimer yourCountDownTimer;
    ProgressDialog progressDialog ;
    Button load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.video_list);
        load = findViewById(R.id.more);
        progressDialog = ProgressDialog.show(this, null, "Loading ...", true, false);

        adapter = new VideosAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
//
//
        if (!MyApplication.hasNetwork()) {
            AlertMessage.alert(MainActivity.this, MainActivity.this.getResources().getString(R.string.internet));
        }
        else {
            search("", false);
        }
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load more data
                load.setText(getString(R.string.wait));
                if (!MyApplication.hasNetwork()) {
                    AlertMessage.alert(MainActivity.this, MainActivity.this.getResources().getString(R.string.internet));
                }
                else {
                    Log.d("search","else");
                    search("", true);
                }
            }
        });

    }
    /**
     * call this method to get response from youtube API.
     *
     * @param query String value to search on google, Empty string means get all videos.
     * @param more  if you want to load next page then pass true, this means add new items at bottom of RecyclerView.
     */
    private void search(String query, final boolean more) {
        progressDialog.show();


        VideoListViewModel mViewModel = ViewModelProviders.of(this).get(VideoListViewModel.class);
        mViewModel.init();
        mViewModel.getVideos().observe(this, new Observer<YoutubeResponse>() {
            @Override
            public void onChanged(@Nullable YoutubeResponse videoModels) {
                progressDialog.cancel();
                if (videoModels.getItems() != null) {
                    List<YoutubeResponse.Item> items = videoModels.getItems();
                    lastToken = videoModels.getNextPageToken();
                    Log.d("token",lastToken);
//                    lastToken = videoModels.getNextPageToken();
                    if (more) {
                        adapter.addAll(items);
                    } else {
                        adapter.replaceWith(items);
                    }
                }
//                adapter.notifyDataSetChanged();
                load.setText(getString(R.string.load));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (!MyApplication.hasNetwork()){
            yourCountDownTimer = new CountDownTimer(5*60*1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub
                    if (!MyApplication.hasNetwork()) {
                        AlertMessage.alert(MainActivity.this, MainActivity.this.getResources().getString(R.string.internet));
                    }
                    else {
                        yourCountDownTimer.cancel();
                    }
                }
            }.start();
        }
        else {
            if (yourCountDownTimer!=null) {
                yourCountDownTimer.cancel();
            }
        }
        //load data from api.
//        Toast.makeText(this, "resume", Toast.LENGTH_SHORT).show();
    }

}

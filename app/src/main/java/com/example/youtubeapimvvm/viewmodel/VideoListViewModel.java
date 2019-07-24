package com.example.youtubeapimvvm.viewmodel;



import android.util.Log;

import com.example.youtubeapimvvm.api.ServiceGenerator;
import com.example.youtubeapimvvm.models.YoutubeResponse;
import com.example.youtubeapimvvm.utils.Constants;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListViewModel extends ViewModel {
    private static final String TAG = VideoListViewModel.class.getSimpleName();

    // TODO: Implement the ViewModel
    private MutableLiveData<YoutubeResponse> data;

    public  String lastToken;


    public VideoListViewModel() {
        data = new MutableLiveData<>();
    }

    public void init() {

        Call<YoutubeResponse> call = ServiceGenerator.getApi().popularVideos("mostPopular","IN" , Constants.YOUTUBE_API_KEY, "snippet,id", "10", lastToken);
        Log.d("repTest",".....");

        call.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                if(response.body()!=null)
                {
                    YoutubeResponse body = response.body();
                    lastToken = body.getNextPageToken();
                    Log.d("rep",lastToken);
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public MutableLiveData<YoutubeResponse> getVideos() {
        return this.data;
    }
}

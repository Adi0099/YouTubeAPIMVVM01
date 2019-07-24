package com.example.youtubeapimvvm.api;

import com.example.youtubeapimvvm.models.YoutubeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://www.googleapis.com/youtube/v3/videos?chart=mostPopular&key=AIzaSyCaRkNQ3KJ6flP2fpY35arzkiBTZBxC-sg&part=snippet&maxResults=50
public interface YoutubeApi {
    @GET("search")
    Call<YoutubeResponse> searchVideo(@Query("q") String query,
                                      @Query("type") String type,
                                      @Query("key") String key,
                                      @Query("part") String part,
                                      @Query("maxResults") String maxResults,
                                      @Query("pageToken") String pageToken);

    @GET("videos")
    Call<YoutubeResponse> popularVideos(@Query("chart") String query,
                                        @Query("regionCode") String region,
                                        @Query("key") String key,
                                        @Query("part") String part,
                                        @Query("maxResults") String maxResults,
                                        @Query("pageToken") String pageToken);
}

package com.example.photogallery.api;

import com.example.photogallery.model.FlickrPhotos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrAPI {
    @GET("services/rest/?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    Call<FlickrPhotos> getRecent(@Query("api_key") String api_key);

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<FlickrPhotos> getSearchPhotos(@Query("api_key") String api_key, @Query("text") String keyWord);
}

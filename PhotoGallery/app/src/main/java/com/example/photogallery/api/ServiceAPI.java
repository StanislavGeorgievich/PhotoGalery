package com.example.photogallery.api;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAPI {
    private static final String BASE_URL = "https://flickr.com/";
    private static Retrofit retrofit = null;
    private static FlickrAPI flickr_api = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new Gson();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    public static FlickrAPI getFlickrAPI() {
        if (flickr_api == null) {
            flickr_api = getRetrofit().create(FlickrAPI.class);
        }

        return flickr_api;
    }
}

package com.example.photogallery.model;

import com.google.gson.annotations.SerializedName;

public class FlickrPhotos
{
    private Photos photos;
    private String stat;

    public FlickrPhotos() {}

    public FlickrPhotos(Photos photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    public Photos getPhotos() {
        return photos;
    }

    @SerializedName("photos")
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    @SerializedName("stat")
    public void setStat(String stat) {
        this.stat = stat;
    }
}

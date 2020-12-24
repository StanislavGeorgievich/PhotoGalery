package com.example.photogallery.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Photos
{
    private int page;
    private int pages;
    private int perpage;
    private String total;
    private List<Photo> photo;

    public Photos() {}

    public Photos(List<Photo> photo, int page, int pages, int perpage, String total) {
        setPhoto(photo);
        setPage(page);
        setPages(pages);
        setPerpage(perpage);
        setTotal(total);
    }

    public int getPage() {
        return page;
    }

    @SerializedName("page")
    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    @SerializedName("pages")
    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    @SerializedName("perpage")
    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    @SerializedName("total")
    public void setTotal(String total) {
        this.total = total;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    @SerializedName("photo")
    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }
}

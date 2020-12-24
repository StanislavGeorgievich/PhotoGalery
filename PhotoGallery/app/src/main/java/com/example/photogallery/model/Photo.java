package com.example.photogallery.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Photos")
public class Photo
{
    @PrimaryKey
    @NonNull
    private String id;
    private String owner;
    private String secret;
    private String server;
    private String title;
    private int farm;
    private int isFamily;
    private int isPublic;
    private int isFriend;
    private boolean isDb;

    public Photo() {
        this("", "", "", "", "", 0, 0, 0, false);
    }

    public Photo(String id, String owner, String secret, String server, String title, int isFamily, int isFriend, int isPublic, boolean isDb) {
        setId(id);
        setOwner(owner);
        setSecret(secret);
        setServer(server);
        setTitle(title);
        setIsFamily(isFamily);
        setIsFriend(isFriend);
        setIsPublic(isPublic);
        setIsDb(isDb);
    }

    public String getId() {
        return id;
    }

    @SerializedName("id")
    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    @SerializedName("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    @SerializedName("secret")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    @SerializedName("server")
    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    @SerializedName("farm")
    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    @SerializedName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsPublic() {
        return isPublic;
    }

    @SerializedName("ispublic")
    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getIsFriend() {
        return isFriend;
    }

    @SerializedName("isfriend")
    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getIsFamily() {
        return isFamily;
    }

    @SerializedName("isfamily")
    public void setIsFamily(int isFamily) {
        this.isFamily = isFamily;
    }

    public boolean getIsDb() {
        return isDb;
    }

    public void setIsDb(boolean isDb) {
        this.isDb = isDb;
    }

    //Получить url изображения
    public String getPhotoUrl() {
        return String.format("https://live.staticflickr.com/%s/%s_%s_w.jpg", getServer(), getId(), getSecret());
    }
}

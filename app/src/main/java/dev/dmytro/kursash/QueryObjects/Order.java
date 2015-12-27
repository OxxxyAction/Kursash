package dev.dmytro.kursash.QueryObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmytro on 25.12.2015.
 */
public class Order {
    public static final String host = "http://dbm.pe.hu/web/";
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("profile_id")
    @Expose
    private int profile_id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("descr")
    @Expose
    private String description;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("isDone")
    @Expose
    private int done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        if(picture.contains(host))
        return picture;
        else
        return host+picture;
    }

    public void setPicture(String picture) {
        this.picture = host+picture;
    }

    public int isDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

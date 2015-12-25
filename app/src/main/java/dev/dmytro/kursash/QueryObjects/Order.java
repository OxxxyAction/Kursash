package dev.dmytro.kursash.QueryObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit.mime.TypedFile;

/**
 * Created by Dmytro on 25.12.2015.
 */
public class Order {
    @SerializedName("profile_id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("descr")
    @Expose
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

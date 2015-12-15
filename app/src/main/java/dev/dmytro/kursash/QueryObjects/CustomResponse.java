package dev.dmytro.kursash.QueryObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmytro on 15.12.2015.
 */
public class CustomResponse {
    @SerializedName("success")
    @Expose
    private boolean success;


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

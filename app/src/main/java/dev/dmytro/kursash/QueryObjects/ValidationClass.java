package dev.dmytro.kursash.QueryObjects;

import com.google.gson.annotations.Expose;

/**
 * Created by Dmytro on 15.12.2015.
 */
public class ValidationClass {
    @Expose
    private String field;
    @Expose
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

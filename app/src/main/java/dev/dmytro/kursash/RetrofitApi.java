package dev.dmytro.kursash;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Dmytro on 15.12.2015.
 */
public interface RetrofitApi {

    @GET("/users")
    public void getUsers(Callback<User> callback);
    @FormUrlEncoded
    @POST("/login")
    public void login(@Field("username") String login, @Field("password") String password, Callback<User> callback);
}

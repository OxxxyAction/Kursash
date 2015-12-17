package dev.dmytro.kursash;

import android.telecom.Call;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.Profile;
import dev.dmytro.kursash.QueryObjects.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
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

    @GET("/profiles/{id}")
    public void getProfile(@Path("id") int id, @Query("access_token") String token, Callback<Profile> callback);

    @FormUrlEncoded
    @POST("/send-email")
    public void sendEmail(@Field("email") String email, Callback<CustomResponse> callback);

    @FormUrlEncoded
    @PUT("/profiles/{id}")
    public void changeProfile(@Path("id") int id, @Query("access_token") String token, @Body Profile profile, Callback<Profile> callback);
}

package dev.dmytro.kursash;

import android.telecom.Call;
import android.util.Log;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.Order;
import dev.dmytro.kursash.QueryObjects.Profile;
import dev.dmytro.kursash.QueryObjects.User;
import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Dmytro on 15.12.2015.
 */
public class RetrofitService {

    public static String token;
    public static int id;
    String BASE_URL = "http://dbm.pe.hu/web/api/";
    RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .build();
    RetrofitApi service = restAdapter.create(RetrofitApi.class);

    public void getUserr(Callback<User> callback){
        service.getUsers(callback);
    }

    public void login(String login, String password, Callback<User> callback){
        service.login(login, password, callback);
    }

    public void getProfile(int id, Callback<Profile> callback){
        service.getProfile(id, token, callback);
    }

    public void sendEmail(String email, Callback<CustomResponse> callback){
        service.sendEmail(email, callback);
    }

    public void changeProfile(Profile profile, int id, Callback<Profile> callback){
       Log.d("Retrofit", token);
        service.changeProfile(id, token, profile, callback);
    }

    public void sendCodeAndPass(String key, String password, Callback<CustomResponse> callBack){
        service.sendCodeAndPass(password,key, callBack);
    }

    public void registration(String username, String email, String password, Callback<CustomResponse> callback){
        service.register(username, email, password, callback);
    }

    public void sendNewOrder(Order order, TypedFile file, Callback<CustomResponse> callback){
        service.sendNewOrder(token, order.getId(), order.getDate(), order.getDescription(), file, callback);
    }
}

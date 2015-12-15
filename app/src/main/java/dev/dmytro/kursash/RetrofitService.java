package dev.dmytro.kursash;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Query;

/**
 * Created by Dmytro on 15.12.2015.
 */
public class RetrofitService {

    public static String token;
    String BASE_URL = "http://govnotour.pe.hu/web/api";
    RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(BASE_URL)
            .build();
    RetrofitApi service = restAdapter.create(RetrofitApi.class);

    public void getUser(Callback<User> callback){
        service.getUsers(callback);
    }

    public void login(String login, String password, Callback<User> callback){
        service.login(login, password, callback);
    }

}

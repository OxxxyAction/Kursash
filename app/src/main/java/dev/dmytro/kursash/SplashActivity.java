package dev.dmytro.kursash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class SplashActivity extends Activity {
    SharedPreferences sPref;
    public static final String ERR = "unluck";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref = getSharedPreferences("UserData",MODE_PRIVATE);
        int id; String token;
        id = sPref.getInt("id", -6);
        token = sPref.getString("access_token", ERR);
        Log.d("Retrofit", id + token);
        Intent intent = new Intent();
        if(id!=-6&&!token.equals(ERR)) {
            intent.setClass(this, ProfileActivity.class);
            RetrofitService.id = id;
            RetrofitService.token = token;
        }
        else{
            intent.setClass(this, LoginActivity.class);
        }
        finish();
        startActivity(intent);

    }
}

package dev.dmytro.kursash;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class ProfileActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Retrofit", getIntent().getIntExtra("id",-6)+"");
    }
}

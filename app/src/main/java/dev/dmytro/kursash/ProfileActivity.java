package dev.dmytro.kursash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class ProfileActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Retrofit", getIntent().getIntExtra("id",-6)+"");

        findViewById(R.id.btn_main_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                intent.putExtra("id",getIntent().getIntExtra("id",-6) );
                startActivity(intent);
            }
        });
    }
}

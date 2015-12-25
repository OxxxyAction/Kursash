package dev.dmytro.kursash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class OrdersActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderslist);
        findViewById(R.id.btn_back_orders).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}


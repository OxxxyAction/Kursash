package dev.dmytro.kursash;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dev.dmytro.kursash.QueryObjects.Order;
import dev.dmytro.kursash.QueryObjects.Profile;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class OrdersActivity extends ListActivity {
    RetrofitService service;
    Context ctx;
    ArrayList<Order> arrOfOrders;
    ProgressDialog pd;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderslist);
        ctx =this;
        service = new RetrofitService();
        empty = (TextView) findViewById(R.id.txtView_orderlist_empty);
        pd = ProgressDialog.show(ctx, "","Получение заказов", true);
        findViewById(R.id.btn_back_orders).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        service.getProfileWithOrders(new Callback<Profile>() {
            @Override
            public void success(Profile profile, Response response) {
            pd.dismiss();
                if(profile.getOrders().size()>0) {
                    arrOfOrders = profile.getOrders();
                    AdapterOrders adapter = new AdapterOrders(ctx, arrOfOrders);
                    ListView list = getListView();
                    list.setAdapter(adapter);
                }else{
                    empty.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void failure(RetrofitError error) {
                pd.dismiss();
                if(error.getMessage()!=null)
                Log.d("Retrofit", error.getMessage());
                else {
                    Toast.makeText(ctx, "Не удалось получить сведения о заказах, попробуйте позже", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        /*Order temp1 = new Order();
        temp1.setDescription("LOL");
        temp1.setDate("2015-12-26");
        temp1.setProfile_id(4);
        temp1.setDone(false);
        Order temp2 = new Order();
        temp2.setDescription("LOL1");
        temp2.setDate("2015-12-27");
        temp2.setProfile_id(4);
        temp2.setDone(false);
        Order temp3 = new Order();
        temp3.setDescription("LOL2");
        temp3.setDate("2015-12-28");
        temp3.setProfile_id(4);
        temp3.setDone(true);
        arrOfOrders = new ArrayList<>();
        arrOfOrders.add(temp1);
        arrOfOrders.add(temp2);
        arrOfOrders.add(temp3);*/

    }
}


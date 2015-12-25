package dev.dmytro.kursash;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.Order;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;

public class NewOrderActivity extends Activity {
    EditText description;
    RetrofitService service;
    Context ctx;
    ProgressDialog pd;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder);

        description = (EditText) findViewById(R.id.edt_neworder_description);
        imgView = (ImageView) findViewById(R.id.imgView_neworder);
        ctx = this;
        service = new RetrofitService();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile("storage/emulated/0/Artwork.jpg", options);
        imgView.setImageBitmap(bitmap);

        findViewById(R.id.btn_neworder_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = ProgressDialog.show(ctx, "", "Ваш запрос обрабатывается");
                Order order = new Order();
                order.setId(RetrofitService.id);
                order.setDescription(description.getText().toString());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date today = Calendar.getInstance().getTime();
                order.setDate(df.format(today));
                service.sendNewOrder(order,new TypedFile("image/*",new File("storage/emulated/0/Artwork.jpg")),new Callback<CustomResponse>() {
                    @Override
                    public void success(CustomResponse customResponse, Response response) {
                        if(customResponse.isSuccess())
                        {
                            Toast.makeText(ctx, "Заказ успешно отправлен", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            finish();
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(ctx, "Ошибка соединения, попробуйте снова", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Retrofit", error.getMessage());
                        String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                        Log.d("Retrofit", json.toString());
                        pd.dismiss();
                        Toast.makeText(ctx, "Что-то пошло не так(", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}

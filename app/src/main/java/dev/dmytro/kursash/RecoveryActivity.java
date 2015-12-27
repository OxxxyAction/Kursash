package dev.dmytro.kursash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.ValidationClass;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class RecoveryActivity extends Activity {
    EditText edt;
    RetrofitService service;
    Context ctx;
    TextView txtViewError;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        ctx = this;
        edt = (EditText) findViewById(R.id.edt_recovery_login);
        txtViewError = (TextView) findViewById(R.id.txtView_recovery_err);
        service = new RetrofitService();

        findViewById(R.id.btn_back_recovery_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_recovery_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = ProgressDialog.show(ctx, "", "Загрузка", true);
                txtViewError.setVisibility(View.GONE);
                service.sendEmail(edt.getText().toString(), new Callback<CustomResponse>() {
                    @Override
                    public void success(CustomResponse customResponse, Response response) {
                        pd.dismiss();
                        if (customResponse.isSuccess()) {
                            Intent intent = new Intent(ctx, RecoveryCodeActivity.class);
                            finish();
                            startActivity(intent);
                        }
                        else{
                            txtViewError.setVisibility(View.VISIBLE);
                            txtViewError.setText("Не удалось подключиться к серверу, попробуйте позже");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pd.dismiss();
                        if (error.getResponse()!=null) {
                        Type type = new TypeToken<List<ValidationClass>>() {
                        }.getType();
                        List<ValidationClass> arr = (List<ValidationClass>) error.getBodyAs(type);
                        String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                        Log.d("Retrofit", json.toString());


                            txtViewError.setVisibility(View.VISIBLE);
                            txtViewError.setText("");
                            for (int i = 0; i < arr.size(); i++) {
                                txtViewError.append(arr.get(i).getMessage() + "\n");
                            }
                        } else {
                            txtViewError.setVisibility(View.VISIBLE);
                            txtViewError.setText("Не удалось подключиться к серверу, попробуйте позже");
                        }
                    }
                });
            }
        });





    }
}

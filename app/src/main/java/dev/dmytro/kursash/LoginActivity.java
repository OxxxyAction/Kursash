package dev.dmytro.kursash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dev.dmytro.kursash.QueryObjects.CustomResponse;
import dev.dmytro.kursash.QueryObjects.User;
import dev.dmytro.kursash.QueryObjects.ValidationClass;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Dmytro on 13.12.2015.
 */
public class LoginActivity extends Activity {
    EditText edtLogin, edtPassword;
    TextView txtViewError;
    Context ctx;
    ProgressDialog pd;
    RetrofitService service;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ctx = this;

        txtViewError = (TextView) findViewById(R.id.txtView_login_error);
        edtLogin = (EditText) findViewById(R.id.edt_login);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        service = new RetrofitService();

        findViewById(R.id.btn_login_auth).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                txtViewError.setVisibility(View.GONE);
                Log.d("Retrofit",  edtPassword.getText().toString() +"  " +  edtLogin.getText().toString());
                if(edtLogin.getText().toString().equalsIgnoreCase("") && edtPassword.getText().toString().equalsIgnoreCase(""))
                    txtViewError.setVisibility(View.VISIBLE);
                else{
                    pd = ProgressDialog.show(LoginActivity.this,"","Загрузка...", true);
                    service.login(edtLogin.getText().toString(), edtPassword.getText().toString(), new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {
                            RetrofitService.token = user.getAccessToken();
                            RetrofitService.id = user.getId();
                            sPref = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putInt("id", user.getId());
                            ed.putString("access_token", user.getAccessToken());
                            ed.apply();
                            ed.commit();

                            Intent intent = new Intent(ctx, ProfileActivity.class);
                            intent.putExtra("id", user.getId());
                            finish();
                            pd.dismiss();
                            startActivity(intent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            pd.dismiss();
                            if (error.getResponse() != null) {
                                Type type = new TypeToken<List<ValidationClass>>() {
                                }.getType();
                                List<ValidationClass> arr = (List<ValidationClass>) error.getBodyAs(type);
                                String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                                Log.d("Retrofit", json.toString());
                                if (error != null && arr.size() > 0) {

                                    txtViewError.setVisibility(View.VISIBLE);
                                    txtViewError.setText("");
                                    for (int i = 0; i < arr.size(); i++) {
                                        txtViewError.append(arr.get(i).getMessage() + "\t");
                                    }
                                } else {
                                    txtViewError.setVisibility(View.VISIBLE);
                                    txtViewError.setText("Не удалось подключиться к серверу, попробуйте позже");
                                }
                            } else {
                                txtViewError.setVisibility(View.VISIBLE);
                                txtViewError.setText("Не удалось подключиться к серверу, попробуйте позже");
                            }
                        }
                    });
                }

            }
        });

        findViewById(R.id.btn_login_registration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, RegistrationActivity.class));

            }
        });
        findViewById(R.id.txtView_login_forgetpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, RecoveryActivity.class));
            }
        });
    }
}

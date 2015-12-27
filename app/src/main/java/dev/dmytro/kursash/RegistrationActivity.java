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
public class RegistrationActivity extends Activity {
    EditText username, email, password;
    TextView username_err, email_err, password_err;
    RetrofitService service;
    Context ctx;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = (EditText) findViewById(R.id.edt_reg_login);
        email = (EditText) findViewById(R.id.edt_reg_mail);
        password = (EditText) findViewById(R.id.edt_reg_pass_first);

        username_err = (TextView) findViewById(R.id.txtView_reg_login);
        email_err = (TextView) findViewById(R.id.txtView_reg_err_email);
        password_err = (TextView) findViewById(R.id.txtView_reg_err_pass);

        service = new RetrofitService();
        ctx = this;

        findViewById(R.id.btn_back_registration_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_reg_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setErrorsInvisible();
                pd = ProgressDialog.show(ctx, "", "Отправка данных", true);
                service.registration(username.getText().toString(), email.getText().toString(), password.getText().toString(), new Callback<CustomResponse>() {
                    @Override
                    public void success(CustomResponse customResponse, Response response) {
                        pd.dismiss();
                        if (customResponse.isSuccess()) {
                            Toast.makeText(ctx, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ctx, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ctx, "При регистрации возникла ошибка, попробуйте снова.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pd.dismiss();
                        if(error.getResponse()!=null){
                        Type type = new TypeToken<List<ValidationClass>>() {
                        }.getType();
                        List<ValidationClass> arr = (List<ValidationClass>) error.getBodyAs(type);
                        String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                        Log.d("Retrofit", json.toString());
                            for (int i = 0; i < arr.size(); i++) {
                                TextView temp = null;
                                switch (arr.get(i).getField()) {
                                    case "username":
                                        temp = username_err;
                                        break;
                                    case "email":
                                        temp = email_err;
                                        break;
                                    case "password":
                                        temp = password_err;
                                        break;
                                    default:
                                        temp = password_err;
                                }
                                temp.setVisibility(View.VISIBLE);
                                temp.setText(arr.get(i).getMessage());
                            }
                        } else {
                            password_err.setVisibility(View.VISIBLE);
                            password_err.setText("Не удалось подключиться к серверу, попробуйте позже");
                        }
                    }
                });
                /*}
                else{
                    password_err.setVisibility(View.VISIBLE);
                    password_err.setText("Пароли не совпадают");
                }*/
            }
        });

    }

    void setErrorsInvisible(){
        username_err.setVisibility(View.GONE);
        email_err.setVisibility(View.GONE);
        password_err.setVisibility(View.GONE);
    }


}

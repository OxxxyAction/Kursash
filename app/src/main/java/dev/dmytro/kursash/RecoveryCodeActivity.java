package dev.dmytro.kursash;

import android.app.Activity;
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
public class RecoveryCodeActivity extends Activity {
    EditText edtCode, edtPass, edtSecondPass;
    TextView errCode, errPass;

    RetrofitService service;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_code);
         ctx = this;
        edtCode = (EditText) findViewById(R.id.edt_recovery_code);
        edtPass = (EditText) findViewById(R.id.edt_recovery_password_first);
        edtSecondPass = (EditText) findViewById(R.id.edt_recovery_password_second);

        errCode = (TextView) findViewById(R.id.txtView_recovery_code_err);
        errPass = (TextView) findViewById(R.id.txtView_recovery_password_err);


        service = new RetrofitService();

        findViewById(R.id.btn_back_recovery_activity_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_recovery_sendpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            errCode.setVisibility(View.GONE);
            errPass.setVisibility(View.GONE);
        if(!edtCode.getText().toString().equalsIgnoreCase("")){
        if(equalsPass()) {
            service.sendCodeAndPass(edtCode.getText().toString(), edtPass.getText().toString(), new Callback<CustomResponse>() {
                @Override
                public void success(CustomResponse customResponse, Response response) {
                    if (customResponse.isSuccess()) {
                        Toast.makeText(ctx, "Пароль успешно восстановлен!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ctx, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ctx, "При восстановлении пароля возникла ошибка, попробуйте снова.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Type type = new TypeToken<List<ValidationClass>>() {
                    }.getType();
                    List<ValidationClass> arr = (List<ValidationClass>) error.getBodyAs(type);
                    String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                    Log.d("Retrofit", json.toString());
                    if (error != null && arr.size() > 0) {
                        for (int i = 0; i < arr.size(); i++) {
                            TextView temp = null;
                            switch (arr.get(i).getField()) {
                                case "secret_key":
                                    temp = errCode;
                                    break;
                                case "password":
                                    temp = errPass;
                                    break;
                                default:
                                    temp = errPass;
                            }
                            temp.setVisibility(View.VISIBLE);
                            temp.setText(arr.get(i).getMessage());
                        }
                    } else {
                        errPass.setVisibility(View.VISIBLE);
                        errPass.setText("Не удалось подключиться к серверу, попробуйте позже");
                    }
                }
            });
        }else {
            errPass.setVisibility(View.VISIBLE);
            errPass.setText("Пароли не совпадают");
        }
        }else{
            errCode.setVisibility(View.VISIBLE);
            errCode.setText("Код не может быть пустым");
        }

            }
        });
    }

    boolean equalsPass(){
        if(!edtPass.getText().toString().equalsIgnoreCase("") && !edtSecondPass.getText().toString().equalsIgnoreCase("")){
            if(edtPass.getText().toString().equals(edtSecondPass.getText().toString())){
                return true;
            }
            else return false;
        }else
            return false;
    }
}

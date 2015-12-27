package dev.dmytro.kursash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import dev.dmytro.kursash.QueryObjects.Profile;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by Dmytro on 17.12.2015.
 */
public class ChangingMyProfile extends Activity{
    RetrofitService service;
    EditText name, surname, age;
    RadioGroup radioSex;
    ProgressDialog pd;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changingprofile);
        ctx = this;
        name = (EditText) findViewById(R.id.edt_profile_name);
        surname = (EditText) findViewById(R.id.edt_profile_lastname);
        age = (EditText) findViewById(R.id.edt_profile_age);
        radioSex = (RadioGroup) findViewById(R.id.radioSex);


        service = new RetrofitService();
        pd = ProgressDialog.show(ctx, "", "Загрузка...", true);
        service.getProfile(new Callback<Profile>() {
            @Override
            public void success(Profile profile, Response response) {
                name.setText(profile.getName());
                surname.setText(profile.getSurname());
                if(profile.getGender().equalsIgnoreCase("мужчина"))
               radioSex.check(radioSex.getChildAt(0).getId());
                else
               radioSex.check(radioSex.getChildAt(1).getId());
                age.setText(profile.getAge()+"");
                pd.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                pd.dismiss();
                Toast.makeText(ctx, "Не удалось получить сведения о профиле, попробуйте позже", Toast.LENGTH_LONG).show();
                finish();
            }
        });




        findViewById(R.id.btn_back_changeprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.btn_changeprofile_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile temp = new Profile();
                if(Integer.parseInt(age.getText().toString()) > 0)
                temp.setAge(Integer.parseInt(age.getText().toString()) );
                else {
                    Toast.makeText(ctx, "Введите корректный возраст", Toast.LENGTH_SHORT).show();
                    return;
                }
                temp.setSurname(surname.getText().toString());
                temp.setName( name.getText().toString() );
                temp.setGender( radioSex.getCheckedRadioButtonId()== radioSex.getChildAt(0).getId()? "Мужчина" : "Женщина" );
                service.changeProfile(temp,  new Callback<Profile>() {
                    @Override
                    public void success(Profile profile, Response response) {
                        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                        intent.putExtra("id", getIntent().getIntExtra("id", -6));
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
//                        String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                       // Log.d("Retrofit", json.toString());
                     Toast.makeText(ctx, "Не удалось сохранить данные на сервер, попробуйте позже", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

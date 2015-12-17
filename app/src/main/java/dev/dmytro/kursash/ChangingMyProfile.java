package dev.dmytro.kursash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import dev.dmytro.kursash.QueryObjects.Profile;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmytro on 17.12.2015.
 */
public class ChangingMyProfile extends Activity{
    RetrofitService service;
    EditText name, firstname, email, age;
    RadioGroup radioSex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changingprofile);
        name = (EditText) findViewById(R.id.edt_profile_name);
        firstname = (EditText) findViewById(R.id.edt_profile_lastname);
        email = (EditText) findViewById(R.id.edt_profile_email);
        age = (EditText) findViewById(R.id.edt_profile_age);
        radioSex = (RadioGroup) findViewById(R.id.radioSex);


        service = new RetrofitService();
        findViewById(R.id.btn_changeprofile_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile temp = new Profile();
                temp.setAge(Integer.parseInt(age.getText().toString()) );
                temp.setEmail( email.getText().toString() );
                temp.setSurname(email.getText().toString());
                temp.setName( email.getText().toString() );
                temp.setGender( radioSex.getCheckedRadioButtonId()==0? "Мужчина" : "Женщина" );
                service.changeProfile(temp, getIntent().getIntExtra("id", -6), new Callback<Profile>() {
                    @Override
                    public void success(Profile profile, Response response) {
                        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                        intent.putExtra("id", getIntent().getIntExtra("id", -6));
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });
    }
}

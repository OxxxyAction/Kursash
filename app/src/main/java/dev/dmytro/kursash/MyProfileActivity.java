package dev.dmytro.kursash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import dev.dmytro.kursash.QueryObjects.Profile;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MyProfileActivity extends Activity {

    TextView name, lastname, email, gender, age;
    RetrofitService service;
    ProgressDialog pd;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        ctx = this;
        pd = ProgressDialog.show(ctx, "", "Загрузка...", true);

        name = (TextView) findViewById(R.id.txtView_profile_firstname);
        lastname = (TextView) findViewById(R.id.txtView_profile_lastname);
        email = (TextView) findViewById(R.id.txtView_profile_email);
        gender = (TextView) findViewById(R.id.txtView_profile_gender);
        age = (TextView) findViewById(R.id.txtView_profile_age);

        service = new RetrofitService();

        findViewById(R.id.btn_back_myprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btn_myprofile_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ChangingMyProfile.class);
                intent.putExtra("id",getIntent().getIntExtra("id", -6) );
                startActivity( intent);
            }
        });

        service.getProfile(new Callback<Profile>() {
            @Override
            public void success(Profile profile, Response response) {
                name.setText(profile.getName());
                lastname.setText(profile.getSurname());
                email.setText(profile.getEmail());
                gender.setText(profile.getGender());
                age.setText(profile.getAge()+"");
                pd.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                pd.dismiss();
                Toast.makeText(ctx, "Не удалось получить сведения о профиле, побробуйте позже", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}

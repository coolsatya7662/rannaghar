package com.plabon.rannaghar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.model.UserResult;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText name,email,mobile,address,password;

    Gson gson = new Gson();
    View progress;
    LocalStorage localStorage;
    String userString;
    User user;
    TextView btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        btnSave = findViewById(R.id.save);
        progress =findViewById(R.id.progressbar);

        Intent intent=getIntent();
        name.setText(intent.getStringExtra("n"));
        mobile.setText(intent.getStringExtra("m"));
        email.setText(intent.getStringExtra("e"));
        address.setText(intent.getStringExtra("a"));

        localStorage = new LocalStorage(getApplicationContext());
        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(name.getText().toString(),mobile.getText().toString(),email.getText().toString(),address.getText().toString(),"1","pass");
                login(user);
            }
        });

    }



    private void login(User user) {
        showProgressDialog();
        Call<UserResult> call = RestClient.getRestService(getApplicationContext()).userupdate(user);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {

                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    UserResult userResult = response.body();
                    if (userResult.getCode() == 200) {
                        String userString = gson.toJson(userResult.getUser());
                        localStorage.createUserLoginSession(userString);
                        Toast.makeText(getApplicationContext(), userResult.getStatus(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainNewActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), userResult.getStatus(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter correct data", Toast.LENGTH_LONG).show();
                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Log.d("Error==> ", t.getMessage());
                hideProgressDialog();
            }
        });
    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }
}
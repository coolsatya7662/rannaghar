package com.plabon.rannaghar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.interfaces.SendOTPApiInterface;
import com.plabon.rannaghar.interfaces.SendOTPLoginApiInterface;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.model.UserResult;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OtpActivity extends AppCompatActivity {

    String otp="",mobile="";
    EditText mobileText,editTextOTP;
    Gson gson = new Gson();
    View progress;
    LocalStorage localStorage;
    String userString;
    User user;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mobileText = findViewById(R.id.login_emailid);
        progress =findViewById(R.id.progressbar);
        loginBtn =findViewById(R.id.loginBtn);
        editTextOTP =findViewById(R.id.login_password);
        Intent intent=getIntent();
        otp= intent.getStringExtra("otp");
        mobile= intent.getStringExtra("mobile");
        mobileText.setText(mobile.toString());

        localStorage = new LocalStorage(getApplicationContext());
        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextOTP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_LONG).show();
                }else {
                    if (editTextOTP.getText().toString().equals(otp.toString())) {
                        user = new User(mobile, otp);
                        login(user);
                    }else{
                        Toast.makeText(getApplicationContext(),"Password Worng",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void login(User user) {
        showProgressDialog();
        Call<UserResult> call = RestClient.getRestService(getApplicationContext()).loginotp(user);
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
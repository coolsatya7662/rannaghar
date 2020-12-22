package com.plabon.rannaghar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.model.UserResult;
import com.plabon.rannaghar.util.CustomToast;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    ImageView imgLogo;
    LinearLayout signPage;

    private static EditText mobile, password;
    private static Button loginButton;
    //private static TextView forgotPassword, signUp;
    //private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    Gson gson = new Gson();
    View progress;
    LocalStorage localStorage;
    String userString;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.loginBtn);
        imgLogo = findViewById(R.id.imglogo);
        signPage = findViewById(R.id.linSign);
        mobile = findViewById(R.id.login_emailid);
        password = findViewById(R.id.login_password);
        progress =findViewById(R.id.progressbar);

        localStorage = new LocalStorage(getApplicationContext());
        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);
        Log.d("User", userString);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.cycle);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"Password sent to your Mobile number",Toast.LENGTH_SHORT).show();
               /* Intent homeIntent= new Intent(LoginActivity.this, MainNewActivity.class);
                startActivity(homeIntent);
                finish();*/
                checkValidation();
            }
        });

        signPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Password sent to your Mobile number",Toast.LENGTH_SHORT).show();
                Intent homeIntent= new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(homeIntent);
                finish();

            }
        });

        imgLogo.startAnimation(animation);

    }


    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        final String getMobile = mobile.getText().toString();
        final String getPassword = password.getText().toString();


        // Check for both field is empty or not
        if (getMobile.equals("") || getMobile.length() == 0) {
//            loginLayout.startAnimation(shakeAnimation);
            mobile.setError("Eneter Mobile number");
            vibrate(200);
        }else if(getPassword.equals("") || getPassword.length() == 0){
 //           loginLayout.startAnimation(shakeAnimation);
            password.setError("Eneter Password");
            vibrate(200);
        }
        else {
            user = new User(getMobile, getPassword);
            login(user);
        }
    }

    private void login(User user) {
        showProgressDialog();
        Call<UserResult> call = RestClient.getRestService(getApplicationContext()).login(user);
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

    public void vibrate(int duration) {
        Vibrator vibs = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(duration);
    }


    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }
}
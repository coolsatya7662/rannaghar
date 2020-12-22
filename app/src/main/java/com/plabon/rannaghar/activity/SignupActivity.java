package com.plabon.rannaghar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.api.clients.RestClient;
import com.plabon.rannaghar.model.User;
import com.plabon.rannaghar.model.UserResult;
import com.plabon.rannaghar.util.CustomToast;
import com.plabon.rannaghar.util.Utils;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private static View view;
    private static EditText fullName, mobileNumber,emailid, password;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    User user;
    LocalStorage localStorage;
    Gson gson = new Gson();
    View progress;

    LinearLayout signPage;
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signPage = findViewById(R.id.linSign);
        imgLogo = findViewById(R.id.imglogo);
        signUpButton = findViewById(R.id.signupBtn);
        fullName = findViewById(R.id.login_name);
        mobileNumber = findViewById(R.id.login_mobile);
        emailid = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        progress =findViewById(R.id.progressbar);


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.cycle);

        signPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Password sent to your Mobile number",Toast.LENGTH_SHORT).show();
                Intent homeIntent= new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        imgLogo.startAnimation(animation);
    }

    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailid.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getPassword = password.getText().toString();
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        //String getZipcode = spinner.getSelectedItem().toString();
        if (getFullName.length() == 0) {
            fullName.setError("Eneter Your Name");
            fullName.requestFocus();
        } else if (getEmailId.length() == 0) {
            emailid.setError("Eneter Correct Email");
            emailid.requestFocus();
        } else if (getMobileNumber.length() == 0) {
            mobileNumber.setError("Eneter Your Mobile Number");
            mobileNumber.requestFocus();
        } else if (getPassword.length() == 0) {
            password.setError("Eneter Password");
            password.requestFocus();
        } else if (getPassword.length() < 6) {
            password.setError("Eneter 6 digit Password");
            password.requestFocus();
        } else {
            user = new User(getFullName, getEmailId, getMobileNumber, getPassword);
            registerUser(user);
            /*  gson = new Gson();
            String userString = gson.toJson(user);


            localStorage.createUserLoginSession(userString);
            progressDialog.setMessage("Registering Data....");
            progressDialog.show();
            Handler mHand = new Handler();
            mHand.postDelayed(new Runnable() {

                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }, 5000);*/
        }

    }

    private void registerUser(User userString) {
        showProgressDialog();
        Call<UserResult> call = RestClient.getRestService(SignupActivity.this).register(userString);
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                Log.d("Response :=>", response.body() + "");
                if (response != null) {

                    UserResult userResult = response.body();
                    if (userResult.getCode() == 201) {
                        //String userString = gson.toJson(userResult.getUser());
                        //localStorage.createUserLoginSession(userString);
                        Toast.makeText(getApplicationContext(), userResult.getStatus(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        new CustomToast().Show_Toast(getApplicationContext(), view,
                                userResult.getStatus());

                    }

                } else {
                    new CustomToast().Show_Toast(getApplicationContext(), view,
                            "Please Enter Correct Data");
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
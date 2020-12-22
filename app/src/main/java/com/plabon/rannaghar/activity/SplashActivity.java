package com.plabon.rannaghar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.plabon.rannaghar.MainNewActivity;
import com.plabon.rannaghar.R;
import com.plabon.rannaghar.util.localstorage.LocalStorage;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASS_TIME_OUT=2000;
    ImageView imgLogo;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.imglogo);
        //localStorage = new LocalStorage(getApplicationContext());

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.cycle);
        imgLogo.startAnimation(animation);

        localStorage = new LocalStorage(getApplicationContext());
       /* if (localStorage.isUserLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), MainNewActivity.class));
            finish();
        }*/


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //Intent homeIntent= new Intent(SplashActivity.this,LoginActivity.class);
                //startActivity(homeIntent);
                //finish();
                if (localStorage.isUserLoggedIn()){
                    Intent homeIntent= new Intent(SplashActivity.this,MainNewActivity.class);
                    startActivity(homeIntent);
                    finish();
                }else{
                    Intent homeIntent= new Intent(SplashActivity.this, MainNewActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        },SPLASS_TIME_OUT);
    }
}
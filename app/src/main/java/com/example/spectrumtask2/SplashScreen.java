package com.example.spectrumtask2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ProgressBar p=findViewById(R.id.progressBarS);
        p.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            FirebaseAuth fAuth=FirebaseAuth.getInstance();
            if(fAuth.getCurrentUser()!=null){
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }else {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
            finish();
        }, 3000);
    }
}
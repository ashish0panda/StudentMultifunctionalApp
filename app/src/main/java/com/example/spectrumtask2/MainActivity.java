package com.example.spectrumtask2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText lEmail,lPassword;
    Button lLogin;
    TextView lRegister;
    ProgressBar lProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fAuth=FirebaseAuth.getInstance();
        lEmail=findViewById(R.id.emailL);
        lPassword=findViewById(R.id.passwordL);
        lLogin=findViewById(R.id.loginL);
        lRegister=findViewById(R.id.registerL);
        lProgress=findViewById(R.id.progressBarL);
        lRegister.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), registration.class)));
        lLogin.setOnClickListener(view -> {
            String email=lEmail.getText().toString().trim();
            String password=lPassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                lEmail.setError("Email is required!");
            }

            else if(TextUtils.isEmpty(password)){
                lPassword.setError("Password is required!");
            }
            else{
                lProgress.setVisibility(View.VISIBLE);
                //Authenticate

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "User is logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Some error occurred " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        lProgress.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
}
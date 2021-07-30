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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class registration extends AppCompatActivity {
    public EditText rFullName, rEmail, rPassword, rConfirm;
    public Button rRegister;
    public TextView rLogin;
    FirebaseAuth fAuth;
    ProgressBar rProgress;
    String userid;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rFullName=findViewById(R.id.usernameR);
        rEmail=findViewById(R.id.emailR);
        rPassword=findViewById(R.id.passwordR);
        rConfirm=findViewById(R.id.conpasswordR);
        rRegister=findViewById(R.id.registerR);
        rLogin=findViewById(R.id.loginR);
        fAuth=FirebaseAuth.getInstance();
        rProgress=findViewById(R.id.progressBarR);
        fStore=FirebaseFirestore.getInstance();
        rRegister.setOnClickListener(view -> {
            String email=rEmail.getText().toString().trim();
            String password=rPassword.getText().toString().trim();
            String confirm=rConfirm.getText().toString();
            String name=rFullName.getText().toString();

            if(TextUtils.isEmpty(email)){
                rEmail.setError("Email is required!");
            }

            else if(TextUtils.isEmpty(password)){
                rPassword.setError("Password is required!");
            }

            else if(password.length()<8){
                rPassword.setError("Password Should have minimum 8 letter!");
            }
            else if(!password.equals(confirm)){
                rConfirm.setError("Password and Confirm Password are not match");
                rPassword.setError("Password and Confirm Password are not match");
            }
            else{
                rProgress.setVisibility(View.VISIBLE);

                //REGISTER USER FIREBASE

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userid= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                        DocumentReference documentReference=fStore.collection("users").document(userid);
                        Map<String,Object> user= new HashMap<>();
                        user.put("name",name);
                        user.put("email",email);
                        documentReference.set(user).addOnSuccessListener(aVoid -> Toast.makeText(registration.this, "User Created", Toast.LENGTH_SHORT).show());
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                    } else {
                        Toast.makeText(registration.this, "Some error occurred " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        rProgress.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        rLogin.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }
}
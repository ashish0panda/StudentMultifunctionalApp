package com.example.spectrumtask2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Dashboard.this,MainActivity.class));
        finish();
    }

    public void pdfMaker(View view) {
        startActivity(new Intent(Dashboard.this,PdfMaker.class));
    }

    public void timeTableViewer(View view) {
        startActivity(new Intent(Dashboard.this,TimeTableViewer.class));
    }

    public void reminder(View view) {
        startActivity(new Intent(Dashboard.this,Reminder.class));
    }

    public void documentStorage(View view) {
        startActivity(new Intent(Dashboard.this,DocumentStorage.class));
    }

    public void toDoList(View view) {
        startActivity(new Intent(Dashboard.this,ToDoList.class));
    }

    public void calculator(View view) {
        startActivity(new Intent(Dashboard.this,Calculator.class));
    }

    public void pocSection(View view) {
        startActivity(new Intent(Dashboard.this,PocSection.class));
    }
}
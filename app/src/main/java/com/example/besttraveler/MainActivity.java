package com.example.besttraveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView signUp = findViewById(R.id.signUpText);
        Button login = findViewById(R.id.loginButton);
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            signUp.setTextColor(Color.parseColor("#FF96E012"));
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            Intent intent = new Intent(this, IndexActivity.class);
            startActivity(intent);
        });

    }
}
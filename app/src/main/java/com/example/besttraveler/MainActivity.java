package com.example.besttraveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView signUp = findViewById(R.id.signUpText);
        Button login = findViewById(R.id.loginButton);
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            signUp.setTextColor(this.getResources().getColor(R.color.pressedHoverColor));
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            Intent intent = new Intent(this, IndexActivity.class);
            startActivity(intent);
        });

    }
}
package com.example.besttraveler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Fragment airline = new airlineFragment();
        Fragment risk = new riskFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.replace(R.id.fragmentContainerView, airline);
        fragmentTransaction.commit();


        Button airlinebutton = findViewById(R.id.airlinebutton);
        airlinebutton.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.fragmentContainerView, airline);
            fragmentTransaction1.commit();
        });

        Button riskbutton = findViewById(R.id.riskbutton);
        riskbutton.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
            fragmentTransaction2.replace(R.id.fragmentContainerView, risk);
            fragmentTransaction2.commit();
        });


        Button next = findViewById(R.id.next);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(this, lastActivity.class);

            startActivity(intent);
        });
    }
}
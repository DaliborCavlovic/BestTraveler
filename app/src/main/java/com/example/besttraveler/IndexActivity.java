package com.example.besttraveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IndexActivity extends AppCompatActivity {

    Button Search, Review;
    EditText startLocation, endLocation, date,Departure,Arrive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Review = findViewById(R.id.reviewButton);
        startLocation = findViewById(R.id.startLocation);
        endLocation = findViewById(R.id.ednLocation);
        date = findViewById(R.id.travelDate);
        Departure=findViewById(R.id.departure);
        Arrive=findViewById(R.id.arrive);


        Review.setOnClickListener(View -> {
            Intent reviewIntent = new Intent(this, reviewActivity.class);
            startActivity(reviewIntent);
        });

        Search = findViewById(R.id.searchbutton);
        Search.setOnClickListener(view -> {
            Intent intent = new Intent(this, ResultActivity.class);
            String start = startLocation.getText().toString();
            String destination = endLocation.getText().toString();
            String departure=Departure.getText().toString();
            String arrive=Arrive.getText().toString();
            String date1 = date.getText().toString();
            intent.putExtra("start", start);
            intent.putExtra("destination", destination);
            intent.putExtra("date", date1);
            intent.putExtra("Dep", departure);
            intent.putExtra("arr", arrive);


            startActivity(intent);
        });
    }
}
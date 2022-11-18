package com.example.besttraveler;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reviewActivity extends AppCompatActivity {

    EditText startLocation, reviewEdit, date;
    Button submitButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String beginPlace, reviewSentence, dateDone;


    @Override
    protected void onCreate(Bundle SavedInstanceData) {
        super.onCreate(SavedInstanceData);
        setContentView(R.layout.activity_review);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        submitButton = findViewById(R.id.reviewSubmit);

        startLocation = findViewById(R.id.startLocationReview);
        reviewEdit = findViewById(R.id.reviewEditText);
        date = findViewById(R.id.travelDateReview);

        submitButton.setOnClickListener(View -> {
            beginPlace = startLocation.getText().toString();
            reviewSentence = reviewEdit.getText().toString();
            dateDone = date.getText().toString();
            Review review = new Review(beginPlace, reviewSentence, dateDone);

            if (checkFields()) {
                sendData(review);
            }
        });
    }

    public void sendData(Review review) {
        String id = databaseReference.push().getKey();

        if (id != null) {
            Task<Void> task = databaseReference.child("Reviews").child(id).setValue(review);
            task.addOnSuccessListener(o -> {
                Toast.makeText(reviewActivity.this, "Review Submitted, Thank you!", Toast.LENGTH_LONG).show();
                startLocation.setText("");
                reviewEdit.getEditableText().clear();
                date.setText("");
            });
        }


    }

    public boolean checkFields() {
        if (startLocation.getText().toString().trim().isEmpty() || reviewEdit.getText().toString().trim().isEmpty()
        || date.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

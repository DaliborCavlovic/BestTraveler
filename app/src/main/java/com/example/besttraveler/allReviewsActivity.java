package com.example.besttraveler;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class allReviewsActivity extends AppCompatActivity {
    Button reviewToggle, previousReview;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<String> reviews, users, locations;

    TextView name, location, review;

    static int counter = 0;

    @Override
    public void onCreate(Bundle SavedInstanceData) {
        super.onCreate(SavedInstanceData);
        setContentView(R.layout.activity_allreviews);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        name = findViewById(R.id.nameOfReview);
        location = findViewById(R.id.nameOfVisitedArea);
        review = findViewById(R.id.contentOfReview);

        reviews = new ArrayList<>();
        users = new ArrayList<>();
        locations = new ArrayList<>();

        reviewToggle = findViewById(R.id.reviewToggle);

        previousReview = findViewById(R.id.previousButton);

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Reviews");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                processDB((Map<String,Object>) dataSnapshot.getValue());
                name.setText(users.get(counter));
                location.setText(locations.get(counter));
                String userReview = "\"";
                userReview += reviews.get(counter);
                userReview += "\"";
                review.setText(userReview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



            reviewToggle.setOnClickListener(View -> {
                counter++;
                if (counter < users.size() && counter < locations.size() && counter < reviews.size()) {
                    name.setText(users.get(counter));
                    location.setText(locations.get(counter));
                    String userReview = "\"";
                    userReview += reviews.get(counter);
                    userReview += "\"";
                    review.setText(userReview);
                } else {
                    Toast.makeText(this, "No more reviews", Toast.LENGTH_SHORT).show();
                    counter--;
                }
            });

            previousReview.setOnClickListener(View -> {
                if (counter > 0) {
                    counter--;
                    if (counter < users.size() && counter < locations.size() && counter < reviews.size()) {
                        name.setText(users.get(counter));
                        location.setText(locations.get(counter));
                        String userReview = "\"";
                        userReview += reviews.get(counter);
                        userReview += "\"";
                        review.setText(userReview);
                    } else {
                        Toast.makeText(this, "No more reviews", Toast.LENGTH_SHORT).show();
                    }
                }
            });




    }

    private void processDB(Map<String,Object> usersList) {
        for (Map.Entry<String, Object> entry : usersList.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            users.add((String) singleUser.get("user"));
            reviews.add((String) singleUser.get("review"));
            locations.add((String) singleUser.get("startLocation"));

            Log.d("PB_USER", "processDB: " + users.get(0));
            Log.d("PB_REVIEW", "processDB: " + reviews.get(0));
            Log.d("PB_START_LOCATION", "processDB: " + locations.get(0));
        }
    }



//    @Override
//    public void onPostCreate(Bundle SavedInstanceData) {
//        super.onPostCreate(SavedInstanceData);
//
//    }

}

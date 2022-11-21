package com.example.besttraveler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class allReviewsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle SavedInstanceData) {
        super.onCreate(SavedInstanceData);
        setContentView(R.layout.activity_allreviews);

        Fragment gallery = new fragment_reviewGallery();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, gallery);
        fragmentTransaction.commit();

    }

}

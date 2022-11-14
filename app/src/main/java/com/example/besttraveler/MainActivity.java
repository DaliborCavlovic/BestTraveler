package com.example.besttraveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    EditText userInput, passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        userInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        TextView signUp = findViewById(R.id.signUpText);
        Button login = findViewById(R.id.loginButton);
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            signUp.setTextColor(Color.parseColor("#FF96E012"));
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            if (checkFields()) {
                login();
            }
        });

    }


    private void login() {
        // get the reference to the JSON tree
        databaseReference = database.getReference();
        String username = userInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        // add a value event listener to the Users node
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            // called to read a static snapshot of the contents at a given path
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean match = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        match = true;
                        Toast.makeText(MainActivity.this, "Access granted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                        startActivity(intent);
                    }
                }
                    if(!match) {
                        Toast.makeText(MainActivity.this, "Access denied", Toast.LENGTH_LONG).show();
                    }
            }

            // called when the client doesn't have permission to access the data
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private boolean checkFields() {
        String username = userInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        if(TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
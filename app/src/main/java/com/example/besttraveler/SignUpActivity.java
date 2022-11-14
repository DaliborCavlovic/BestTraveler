package com.example.besttraveler;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    EditText userInput, passwordInput, passwordInputConfirm;
    Button signupButton;

    public void onCreate(Bundle SavedInstanceData) {
        super.onCreate(SavedInstanceData);
        setContentView(R.layout.activity_signup);

        signupButton = findViewById(R.id.signupButton);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        userInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordInputConfirm = findViewById(R.id.passwordInputConfirm);

        signupButton.setOnClickListener(View -> {
            if (checkFields()) {
                if (passwordMatch() && isValid(userInput.getText().toString(), passwordInput.getText().toString())) {
                    sendData(userInput.getText().toString(), passwordInput.getText().toString());
                }
            }
        });

    }

    private boolean passwordMatch() {
        if (passwordInput.getText().toString().equals(passwordInputConfirm.getText().toString())) {
            return true;
        }
        Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void sendData(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        String id = databaseReference.push().getKey();

        if (id != null) {
            Task<Void> setValueTask = databaseReference.child("Users").child(id).setValue(user);

            setValueTask.addOnSuccessListener(o -> {
                Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_LONG).show();
                userInput.setText("");
                passwordInput.setText("");
                passwordInputConfirm.setText("");
            });
        }
    }

    public boolean isValid(String email, String password) {
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length() > 3) {
            return true;
        }
        Toast.makeText(SignUpActivity.this, "The supplied email is invalid", Toast.LENGTH_SHORT).show();
        return false;
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

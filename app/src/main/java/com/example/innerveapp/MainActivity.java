package com.example.innerveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    */
    public void changeActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, SingleUserActivity.class);
        startActivity(intent);
    }
    public void log(View view){
        Intent intent = new Intent(MainActivity.this, PRLoginActivity.class);
        startActivity(intent);
    }
}

package com.example.innerveapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    CallbackManager callbackManager;
    LoginButton loginButton;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        callbackManager = CallbackManager.Factory.create();

        if(user == null)
        {
            setContentView(R.layout.activity_main);
            FacebookSdk.sdkInitialize(getApplicationContext());
            loginButton=(LoginButton)findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList("email"));


        }else {
            Intent intent = new Intent (MainActivity.this,SingleUserActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void changeActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, SingleUserActivity.class);
        startActivity(intent);
    }

    public void log(String prname){
        Intent intent = new Intent(MainActivity.this, PRLoginActivity.class);
        intent.putExtra("prname", prname);
        startActivity(intent);
    }

    public void googleSignIn(View view)
    {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void buttonclickLoginFb(View view) {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                 loginButton.setEnabled(false);
                handleFacebookToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(),"User cancelled it ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginButton.setEnabled(true);
                            updateUI(user);
                        }else{

                            Toast.makeText(MainActivity.this,"Could not register to firebase",Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);

                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user) {

        Intent accountIntent = new Intent(MainActivity.this,SingleUserActivity.class);
        startActivity(accountIntent);
        finish();
    }
    public void emailSignIn(View view) {
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Empty field, please enter you email and password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // open pr registration page
                            log(user.getDisplayName());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void emailSignOut()
    {
        FirebaseAuth.getInstance().signOut();
    }

}
